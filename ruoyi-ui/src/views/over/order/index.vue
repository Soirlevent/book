<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
<!--      <el-form-item label="图书ISBN" prop="bookIsbn">-->
<!--        <el-input-->
<!--          v-model="queryParams.bookIsbn"-->
<!--          placeholder="请输入图书ISBN"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="用户id" prop="userId">-->
<!--        <el-input-->
<!--          v-model="queryParams.userId"-->
<!--          placeholder="请输入用户id"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="逾期天数" prop="overData">
        <el-input
          v-model="queryParams.overData"
          placeholder="请输入逾期天数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="处罚金额" prop="overMoney">
        <el-input
          v-model="queryParams.overMoney"
          placeholder="请输入处罚金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="支付状态" prop="status">-->
<!--        <el-select v-model="queryParams.status" placeholder="请选择支付状态" clearable>-->
<!--          <el-option-->
<!--            v-for="dict in dict.type.sys_alipay_status"-->
<!--            :key="dict.value"-->
<!--            :label="dict.label"-->
<!--            :value="dict.value"-->
<!--          />-->
<!--        </el-select>-->
<!--      </el-form-item>-->
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
          v-hasPermi="['over:order:add']"
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
          v-hasPermi="['over:order:edit']"
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
          v-hasPermi="['over:order:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['over:order:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column label="逾期订单id" align="center" prop="overId" />
      <el-table-column label="用户账号" align="center" prop="user.userName" />
      <el-table-column label="图书名" align="center" prop="book.bookName" />
      <el-table-column label="ISBN码" align="center" prop="book.bookIsbn" />
      <el-table-column label="图书价格" align="center" prop="bookPrice" />
      <el-table-column label="用户账号" align="center" prop="user.userName" />
      <el-table-column label="逾期天数" align="center" prop="overData" />
      <el-table-column label="逾期率" align="center" prop="overOneday" />
      <el-table-column label="处罚金额" align="center" prop="overMoney" />
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
      <el-table-column label="支付时间" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['over:order:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['over:order:remove']"
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

    <!-- 添加或修改图书逾期对话框 -->
<!--    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>-->
<!--      <el-form ref="form" :model="form" :rules="rules" label-width="80px">-->
<!--        <el-form-item label="图书id" prop="bookId">-->
<!--          <el-input v-model="form.bookId" placeholder="请输入图书id" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="用户id" prop="userId">-->
<!--          <el-input v-model="form.userId" placeholder="请输入用户id" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="逾期天数" prop="overData">-->
<!--          <el-input v-model="form.overData" placeholder="请输入逾期天数" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="逾期率" prop="overOneday">-->
<!--          <el-input v-model="form.overOneday" placeholder="请输入逾期率" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="处罚金额" prop="overMoney">-->
<!--          <el-input v-model="form.overMoney" placeholder="请输入处罚金额" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="支付状态(0未支付 1支付成功 2支付失败)" prop="status">-->
<!--          <el-select v-model="form.status" placeholder="请选择支付状态(0未支付 1支付成功 2支付失败)">-->
<!--            <el-option-->
<!--              v-for="dict in dict.type.sys_alipay_status"-->
<!--              :key="dict.value"-->
<!--              :label="dict.label"-->
<!--:value="dict.value"-->
<!--            ></el-option>-->
<!--          </el-select>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="逻辑删除" prop="isDelet">-->
<!--          <el-input v-model="form.isDelet" placeholder="请输入逻辑删除" />-->
<!--        </el-form-item>-->
<!--      </el-form>-->
<!--      <div slot="footer" class="dialog-footer">-->
<!--        <el-button type="primary" @click="submitForm">确 定</el-button>-->
<!--        <el-button @click="cancel">取 消</el-button>-->
<!--      </div>-->
<!--    </el-dialog>-->
  </div>
</template>

<script>
import { listOrder, getOrder, delOrder, addOrder, updateOrder } from "@/api/over/order";

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
      // 图书逾期表格数据
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
        overData: null,
        overOneday: null,
        overMoney: null,
        status: null,
        bookIsbn: null,
        userName: null,
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
    /** 查询图书逾期列表 */
    getList() {
      this.loading = true;
      listOrder(this.queryParams).then(response => {
        this.orderList = response.rows;
        this.total = response.total;
        this.loading = false;
        console.log(this.orderList);
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
        overId: null,
        bookId: null,
        userId: null,
        overData: null,
        overOneday: null,
        overMoney: null,
        status: null,
        isDelet: null,
        createTime: null,
        updateTime: null
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
      this.ids = selection.map(item => item.overId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加图书逾期";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const overId = row.overId || this.ids
      getOrder(overId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改图书逾期";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.overId != null) {
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
    /** 删除按钮操作 */
    handleDelete(row) {
      const overIds = row.overId || this.ids;
      this.$modal.confirm('是否确认删除图书逾期编号为"' + overIds + '"的数据项？').then(function() {
        return delOrder(overIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('over/order/export', {
        ...this.queryParams
      }, `order_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
