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
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="bookList">
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
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleDetail(scope.row)"
            v-hasPermi="['borrow:book:view']"
          >详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-s-promotion"
            @click="handleBorrow(scope.row)"
            v-hasPermi="['borrow:book:rent']"
          >借阅</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit-outline"
            @click="handleRenew(scope.row)"
            v-hasPermi="['borrow:book:reserve']"
          >预约</el-button>
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
      <el-card  label-width="80px">
        <div>
          <template slot-scope="scope">
            <image-preview :src="scope.row.bookImg" :width="50" :height="50"/>
          </template>
        </div>
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
      </el-card>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listBook, getBook, delBook, addBook, updateBook } from "@/api/borrow/book";
import { listLocation} from "@/api/library/location";
import { listType } from "@/api/library/type";
import {addBorrowByBookId} from "@/api/borrow/borrow";
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
    /**图书借阅*/
    handleBorrow(row){
      if(row.bookNum == '0'){
        return this.$modal.msgWarning("该书籍已全部借出！");
      }
      const bookIds = row.bookId || this.ids;
      this.$modal.confirm('是否确认借阅名称为"' + row.bookName + '"的书籍？').then(function() {
        return addBorrowByBookId(bookIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("借阅成功");
      }).catch(() => {});
    },
    /**图书预约*/
    handleRenew(row){
      if(row.bookNum != '0'){
        return this.$modal.msgWarning("该书籍还有剩余，您无须预约！");
      }
    },
    /**查看详情*/
    handleDetail(row){
      this.reset();
      const bookId=row.bookId;
      getBook(bookId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "书籍详情";
        this.isDisabled = true;
      });
    },
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

  }
};
</script>
