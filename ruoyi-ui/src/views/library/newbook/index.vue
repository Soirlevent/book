<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="书名" prop="bookName">
        <el-input
          v-model="queryParams.bookName"
          placeholder="请输入书名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="ISBN号" prop="bookIsbn">
        <el-input
          v-model="queryParams.bookIsbn"
          placeholder="请输入ISBN号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="作者" prop="bookAuthor">
        <el-input
          v-model="queryParams.bookAuthor"
          placeholder="请输入作者"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="出版社" prop="bookAddress">
        <el-input
          v-model="queryParams.bookAddress"
          placeholder="请输入出版社"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['system:book:import']"
        >导入</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="bookList">
      <el-table-column label="书名" align="center" prop="bookName" />
      <el-table-column label="ISBN号" align="center" prop="bookIsbn" />
      <el-table-column label="作者" align="center" prop="bookAuthor" />
      <el-table-column label="出版社" align="center" prop="bookAddress" />
      <el-table-column label="出版日期" align="center" prop="bookDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.bookDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="价格" align="center" prop="bookPrice" />
      <el-table-column label="图书余量" align="center" prop="bookNum" />
      <el-table-column label="图书介绍" align="center" prop="bookIntroduction" />
      <el-table-column label="备注" align="center" prop="remark" />
    </el-table>

    <pagination
      v-show="total>=0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 用户导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px">
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport" /> 是否更新已经存在的书籍数据库存
          </div>
          <el-link type="info" style="font-size:12px" @click="importTemplate">下载模板</el-link>
        </div>
        <div class="el-upload__tip" style="color:red" slot="tip">提示：仅允许导入“xls”或“xlsx”格式文件！</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listBook  } from "@/api/library/book";
import { getToken } from "@/utils/auth";
export default {
  name: "Book",
  data() {
    return {
      // 遮罩层
      type_ID:[],
      loca_ID:[],
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 书籍管理表格数据
      bookList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 图书导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 设置上传的请求头部
        updateSupport: 0,
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/library/book/importData"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        bookName: null,
        bookIsbn: null,
        typeId: null,
        bookImg: null,
        bookAuthor: null,
        bookIntroduction: null,
        bookAddress: null,
        bookDate: null,
        bookPrice: null,
        bookNum: null,
        locaId: null,
        creatBy: null,
      },
      // 表单参数
      form: {},
      // 表单校验

    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询书籍管理列表 */
    getList() {
      this.loading = true;
      listBook(this.queryParams).then(response => {
        this.bookList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        bookId: null,
        bookName: null,
        bookIsbn: null,
        typeId: null,
        bookImg: null,
        bookAuthor: null,
        bookIntroduction: null,
        bookAddress: null,
        bookDate: null,
        bookPrice: null,
        bookNum: null,
        locaId: null,
        creatBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },

    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "图书导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('/library/book/importTemplate',{
      },`book_template_${new Date().getTime()}.xlsx`)
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    }
  }
};
</script>
