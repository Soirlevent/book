<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="支付金额" prop="lossMoney">
        <el-input
          v-model="queryParams.lossMoney"
          placeholder="请输入支付金额"
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
      <el-col :span="24">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['loss:order:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderList">
      <el-table-column label="订单号" align="center" prop="lossId" />
      <el-table-column label="ISBN码" align="center" prop="book.bookIsbn" />
      <el-table-column label="图书名" align="center" prop="book.bookName" />
      <el-table-column label="用户账号" align="center" prop="user.userName" />
      <el-table-column label="用户名" align="center" prop="user.nickName" />
      <el-table-column label="支付金额" align="center" prop="lossMoney" />
      <el-table-column label="支付状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_alipay_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="支付时间" align="center" prop="updataTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updataTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="cancelOrder(scope.row)"
            v-hasPermi="['loss:order:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete-solid"
            @click="handleFind(scope.row)"
            v-hasPermi="['loss:order:cancel']"
          >取挂</el-button>
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
import {listOrder, cancelOrder, updateOrder, addOrder} from "@/api/loss/order";

export default {
  name: "Order",
  dicts: ['sys_alipay_status'],
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
      // 图书遗失订单表格数据
      orderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        bookId: null,
        userId: null,
        lossMoney: null,
        status: null,
        updataTime: null,
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
    /** 查询图书遗失订单列表 */
    getList() {
      this.loading = true;
      listOrder(this.queryParams).then(response => {
        this.orderList = response.rows;
        this.total = response.total;
        this.loading = false;
        console.log(response);
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
        lossId: null,
        bookId: null,
        userId: null,
        lossMoney: null,
        status: null,
        isDelete: null,
        createTime: null,
        updataTime: null
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
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.lossId != null) {
            updateOrder(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOrder(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 取挂按钮操作 */
    handleFind(row) {
      const lossIds = row.lossId || this.ids;
      this.$modal.confirm('是否确认取消图书遗失订单编号为"' + lossIds+"的图书为"+row.book.bookName + '"的数据项？').then(function() {
        return cancelOrder(lossIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("取消成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('loss/order/export', {
        ...this.queryParams
      }, `order_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
