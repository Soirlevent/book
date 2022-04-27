<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
<!--      <el-form-item label="读者号" prop="userId">-->
<!--        <el-input-->
<!--          v-model="queryParams.userId"-->
<!--          placeholder="请输入读者号"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="图书id" prop="bookId">-->
<!--        <el-input-->
<!--          v-model="queryParams.bookId"-->
<!--          placeholder="请输入图书id"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="图书状态" prop="bookFlag">
        <el-select v-model="queryParams.bookFlag" placeholder="请选择图书状态" clearable>
          <el-option
            v-for="dict in dict.type.book_flag_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="续借状态" prop="isRenew">
        <el-select v-model="queryParams.isRenew" placeholder="请选择续借状态" clearable>
          <el-option
            v-for="dict in dict.type.book_renew_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
<!--      <el-form-item label="还书状态" prop="isLeft">-->
<!--        <el-select v-model="queryParams.isLeft" placeholder="请选择还书状态(0:未还;1:已还)" clearable>-->
<!--          <el-option-->
<!--            v-for="dict in dict.type.book_left_status"-->
<!--            :key="dict.value"-->
<!--            :label="dict.label"-->
<!--            :value="dict.value"-->
<!--          />-->
<!--        </el-select>-->
<!--      </el-form-item>-->
      <el-form-item label="遗失状态" prop="isLoss">
        <el-select v-model="queryParams.isLoss" placeholder="请选择遗失状态" clearable>
          <el-option
            v-for="dict in dict.type.lib_book_loss"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="borrowList">
      <el-table-column label="读者账号" align="center" prop="user.userName" />
      <el-table-column label="读者姓名" align="center" prop="user.nickName" />
      <el-table-column label="书籍ISBN" align="center" prop="book.bookIsbn" />
      <el-table-column label="书籍名" align="center" prop="book.bookName" />
      <el-table-column label="图书状态" align="center" prop="bookFlag">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.book_flag_status" :value="scope.row.bookFlag"/>
        </template>
      </el-table-column>
      <el-table-column label="借阅时间" align="center" prop="borrowTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.borrowTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="到期时间" align="center" prop="expireTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.expireTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
<!--      <el-table-column label="还书时间" align="center" prop="returnTime" width="180">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{ parseTime(scope.row.returnTime, '{y}-{m}-{d}') }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column label="续借状态" align="center" prop="isRenew">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.book_renew_status" :value="scope.row.isRenew"/>
        </template>
      </el-table-column>
<!--      <el-table-column label="还书状态" align="center" prop="isLeft">-->
<!--        <template slot-scope="scope">-->
<!--          <dict-tag :options="dict.type.book_left_status" :value="scope.row.isLeft"/>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column label="遗失状态" align="center" prop="isLoss">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.lib_book_loss" :value="scope.row.isLoss"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-s-promotion"
            @click="handleRenew(scope.row)"
            v-hasPermi="['borrow:borrow:renew']"
          >续借</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-thumb"
            @click="handleReturn(scope.row)"
            v-hasPermi="['borrow:borrow:return']"
          >还书</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-switch-button"
            @click="handleLost(scope.row)"
            v-hasPermi="['borrow:borrow:loss']"
          >挂失</el-button>
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

  </div>
</template>

<script>
import { listBorrow,updateFlaglossByborrowId,updateRenewByborrowId,updateReturn} from "@/api/borrow/borrow";

export default {
  name: "Borrow",
  dicts: ['book_flag_status', 'book_renew_status', 'lib_book_loss', 'book_left_status'],
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
      // 借阅信息表格数据
      borrowList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        bookId: null,
        bookFlag: null,
        isRenew: null,
        isLeft: null,
        isLoss: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /**挂失按钮*/
    handleLost(row){
      const borrowIds= row.borrowId || this.ids;
      this.$modal.confirm('是否确认挂失名称为"' + row.book.bookName + '"的书籍？').then(function() {
        return updateFlaglossByborrowId(borrowIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("挂失成功");
      }).catch(() => {});
    },
    /** 还书按钮操作 */
    handleReturn(row) {
      this.reset();
      const id = row.borrowId || this.ids
      this.$modal.confirm('是否确认归还名称为"' + row.book.bookName + '"的书籍？').then(function() {
        return updateReturn(id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("成功归还");
      }).catch(() => {});
    },
    /** 查询借阅信息列表 */
    getList() {
      this.loading = true;
      listBorrow(this.queryParams).then(response => {
        console.log(response);
        this.borrowList = response.rows;
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
        borrowId: null,
        userId: null,
        bookId: null,
        bookFlag: null,
        borrowTime: null,
        expireTime: null,
        returnTime: null,
        isRenew: null,
        isLeft: null,
        isLoss: null,
        isDelete: null
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
    /**查看详情*/
    handleDetail(row){
      this.reset();
      const bookId=row.bookId;
      getBookInfo(bookId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "书籍详情";
        this.isDisabled = true;
      });
    },
    /**图书续借*/
    handleRenew(row){
      if (row.isRenew == '1') {
        return this.$modal.msgWarning("该书籍已续借，您无法续借！");
      }
      if(row.bookFlag == '1'){
        return this.$modal.msgWarning("该书籍逾期，您无法续借！");
      }
      const borrowIds = row.borrowId || this.ids;
      this.$modal.confirm('是否确认续借名称为"' + row.book.bookName + '"的书籍？').then(function() {
        return updateRenewByborrowId(borrowIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("续借成功");
      }).catch(() => {});
    },
  }
};
</script>
