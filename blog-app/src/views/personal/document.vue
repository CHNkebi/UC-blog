<template>
  <div>
    <!-- 个人信息 -->
    <el-card class="me-personal-card">
      <el-form ref="form" :model="form" label-width="290px">
        <!-- 头像修改-待接口 -->
        <el-form-item style="text-align: center" label-width="0">
          <el-upload
            class="avatar-uploader"
            action="http://localhost:8888/upload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
          >
            <img style="width: 40%" v-if="form.avatar" :src="form.avatar" />
          </el-upload>
          <span>点击头像即可修改头像</span>
        </el-form-item>

        <el-form-item label="用户名" class="me-form-item">
          <el-input v-model="form.account" disabled></el-input>
        </el-form-item>
        <el-form-item label="昵称" class="me-form-item">
          <el-input v-model="form.nickName" :disabled="!isUpdate"></el-input>
        </el-form-item>
      </el-form>
      <div style="text-align: center">
        <el-button v-if="!isUpdate" type="primary" @click="isUpdate = !isUpdate"
          >修改个人信息</el-button
        >
        <template v-else-if="isUpdate">
          <el-button type="primary" @click="update('form')">保存</el-button>
          <el-button @click="cancel">取消</el-button>
        </template>
        <br />
        <el-button type="text" @click="changePwd('ruleForm')"
          >修改密码？</el-button
        >
      </div>
    </el-card>

    <!-- 修改密码对话框 -->
    <el-dialog
      title="修改密码"
      :visible.sync="updatePasswordDialog"
      width="400px"
    >
      <el-form
        :model="ruleForm"
        status-icon
        :rules="rules"
        ref="ruleForm"
        label-width="80px"
        class="demo-ruleForm"
        style="margin-right: 30px"
      >
        <el-form-item label="旧密码" prop="old">
          <el-input type="password" v-model.number="ruleForm.old"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="pass">
          <el-input
            type="password"
            v-model="ruleForm.pass"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input
            type="password"
            v-model="ruleForm.checkPass"
            autocomplete="off"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="updatePasswordDialog = false">取 消</el-button>
        <el-button type="primary" @click="updatePassword('ruleForm')"
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>



<script>
export default {
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.ruleForm.checkPass !== "") {
          this.$refs.ruleForm.validateField("checkPass");
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.ruleForm.pass) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      form: {
        login: "",
        account: "",
        avatar: "",
        nickName: "",
      },
      ruleForm: {
        old: "",
        pass: "",
        checkPass: "",
      },
      isUpdate: false,
      updatePasswordDialog: false,
      rules: {
        pass: [{ validator: validatePass, trigger: "blur" }],
        checkPass: [{ validator: validatePass2, trigger: "blur" }],
      },
    };
  },
  mounted() {
    this.init();
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },

    init() {
      // 加载缓存
      this.form.login = this.$store.state.account.length != 0;
      this.form.account = this.$store.state.account;
      this.form.avatar = this.$store.state.avatar;
      this.form.nickName = this.$store.state.name;
    },
    cancel() {
      this.isUpdate = !this.isUpdate;
      this.init();
    },
    //更新用户信息
    update(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          alert("submit!");
        } else {
          console.log("error submit!!");
          return false;
        }
      });
      this.isUpdate = !this.isUpdate;
    },
    // 更新用户密码信息
    changePwd(formName) {
      this.updatePasswordDialog = true;
      // 每次刷新表单
      this.$nextTick(() => {
        this.$refs[formName].resetFields();
      });
    },

    updatePassword(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          alert("submit!");
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
  },
};
</script>

<style scoped>
.me-personal-card {
  width: 740px;
  padding: 80px 30px;
  margin: 0 30px;
}
.me-form-item {
  width: 70%;
}
</style>