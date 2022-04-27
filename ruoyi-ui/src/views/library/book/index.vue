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
        <el-form-item label="图书类型" prop="typeId">
          <el-select v-model="queryParams.typeId" placeholder="请选择图书种类" clearable size="small">
            <el-option
              v-for="id in typeimform"
              :key="id.typeimform"
              :value="id.typeId"
              :label="id.typeName"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="图书位置" prop="typeId">
          <el-select v-model="queryParams.locaId" placeholder="请选择图书位置" clearable size="small">
            <el-option
              v-for="id in locaimform"
              :key="id.locaimform"
              :value="id.locaId"
              :label="id.locaName"
            ></el-option>
          </el-select>
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
                type="primary"
                plain
                icon="el-icon-plus"
                size="mini"
                @click="handleAdd"
                v-hasPermi="['library:book:add']"
              >新增</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="success"
                plain
                icon="el-icon-edit"
                size="mini"
                :disabled="single"
                @click="handleUpdate"
                v-hasPermi="['library:book:edit']"
              >修改</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="danger"
                plain
                icon="el-icon-delete"
                size="mini"
                :disabled="multiple"
                @click="handleDelete"
                v-hasPermi="['library:book:remove']"
              >删除</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="warning"
                plain
                icon="el-icon-download"
                size="mini"
                @click="handleExport"
                v-hasPermi="['library:book:export']"
              >导出</el-button>
            </el-col>
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
          </el-row>

      <el-table v-loading="loading" :data="bookList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="书名" align="center" prop="bookName" />
            <el-table-column label="ISBN号" align="center" prop="bookIsbn" />
            <el-table-column label="图书类型" align="center" prop="type.typeName" />
            <el-table-column label="图书封面" align="center" prop="bookImg" width="100">
              <template slot-scope="scope">
                <image-preview :src="scope.row.bookImg" :width="50" :height="50"/>
              </template>
            </el-table-column>
            <el-table-column label="作者" align="center" prop="bookAuthor" />
            <el-table-column label="图书介绍" align="center" prop="bookIntroduction" />
            <el-table-column label="出版社" align="center" prop="bookAddress" />
            <el-table-column label="出版日期" align="center" prop="bookDate" width="180">
              <template slot-scope="scope">
                <span>{{ parseTime(scope.row.bookDate, '{y}-{m}-{d}') }}</span>
              </template>
            </el-table-column>
            <el-table-column label="价格" align="center" prop="bookPrice" />
            <el-table-column label="图书余量" align="center" prop="bookNum" />
            <el-table-column label="书籍位置" align="center" prop="loca.locaName" />
            <el-table-column label="备注" align="center" prop="remark" />
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="handleUpdate(scope.row)"
                  v-hasPermi="['library:book:edit']"
                >修改</el-button>
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="handleDelete(scope.row)"
                  v-hasPermi="['library:book:remove']"
                >删除</el-button>
              </template>
            </el-table-column>
          </el-table>

      <pagination
            v-show="total>0"
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="getList"
          />


    <!-- 添加或修改书籍管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="书名" prop="bookName">
              <el-input v-model="form.bookName" placeholder="请输入书名" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="ISBN号" prop="bookIsbn">
              <el-input v-model="form.bookIsbn" placeholder="请输入ISBN号" clearable></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="作者" prop="bookPrice">
              <el-input v-model="form.bookAuthor" placeholder="请输入作者" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="价格" prop="bookPrice">
              <el-input v-model="form.bookPrice" placeholder="请输入价格" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="出版社" prop="bookAddress">
              <el-input v-model="form.bookAddress" placeholder="请输入出版社" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="出版日期" prop="bookDate">
              <el-date-picker
                v-model="form.bookDate"
                type="date"
                placeholder="请选择出版日期"
                value-format="yyyy-MM-dd"
                format="yyyy-MM-dd"
              ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="图书类型" prop="typeId">
              <el-select v-model="form.typeId" placeholder="请选择图书类型">
                <el-option
                  v-for="item in typeimform"
                  :key="item.typeId"
                  :label="item.typeName"
                  :value="item.typeId"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="图书位置" prop="locaId">
              <el-select v-model="form.locaId" placeholder="请选择图书位置">
                <el-option
                  v-for="item in locaimform"
                  :key="item.locaId"
                  :label="item.locaName"
                  :value="item.locaId"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="图书封面">
              <image-upload v-model="form.bookImg"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="图书简介">
              <el-input type="textarea" v-model="form.bookDesc" placeholder="请输入图书简介" :autosize="{minRows: 2, maxRows: 5}" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="库存" prop="bookNum">
              <el-input v-model="form.bookNum" placeholder="请输入库存" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" placeholder="请输入备注" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listBook, getBook, delBook, addBook, updateBook } from "@/api/library/book";
import { listLocation} from "@/api/library/location";
import { listType } from "@/api/library/type";
export default {
  name: "Book",
  data() {
    return {
      // 遮罩层
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
        isDelete: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        bookName: [
          { required: true, message: "书名不能为空", trigger: "blur" }
        ],
        bookIsbn: [
          { required: true, message: "ISBN号不能为空", trigger: "blur" },
          { min: 13, max: 13, message: "ISBN号长度为13位", trigger: "blur" },
          { pattern: /^[0-9]*$/, message: "ISBN号只能为数字", trigger: "blur" }
        ],
        bookAuthor: [
          { required: true, message: "作者不能为空", trigger: "blur" }
        ],
        bookAddress: [
          { required: true, message: "出版社不能为空", trigger: "blur" }
        ],
        bookDate: [
          { required: true, message: "出版日期不能为空", trigger: "blur" }
        ],
        bookPrice: [
          { required: true, message: "价格不能为空", trigger: "blur" }
        ],
        bookNum: [
          { required: true, message: "图书剩余数量不能为空", trigger: "blur" },
          { pattern: /^[0-9]*$/, message: "图书剩余数量只能为数字", trigger: "blur" }
        ],
      },

      typeimform:[],
      locaimform:[],

    };
  },
  created() {
    this.getList();
    this.gettypeimform();
    this.getlocaimform();
  },
  methods: {
    gettypeimform(){
      listType().then(res => {
        this.typeimform = res.rows;
      });
    },
    getlocaimform(){
      listLocation().then(res => {
        this.locaimform = res.rows;
      });
    },
    /** 查询书籍管理列表 */
    getList() {
      this.loading = true;
      listBook(this.queryParams).then(response => {
        this.bookList = response.rows;
        this.total = response.total;
        this.loading = false;
        console.log(this.bookList)
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
        isDelete: null,
        createBy: null,
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.bookId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加书籍管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const bookId = row.bookId || this.ids
      getBook(bookId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改书籍管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.bookId != null) {
            updateBook(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBook(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const bookIds = row.bookId || this.ids;
      this.$modal.confirm('是否确认删除书籍管理编号为"' + bookIds + '"的数据项？').then(function() {
        return delBook(bookIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('library/book/export', {
        ...this.queryParams
      }, `book_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
