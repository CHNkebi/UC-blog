<template>
  <div>
    <el-container class="me-personal-main">
      <!-- 个人信息处 -->
      <el-header>
        <div class="me-person-up">
          <!-- 头像区域 -->
          <div class="me-avatar-box">
            <img :src="author.avatar" />
          </div>
          <!-- 简介区 -->
          <div class="me-person-info">
            <div class="me-person-info-1">
              <span>{{ author.nickname }}</span>
              <template v-if="author.id == login.id">
                <el-button
                  size="mini"
                  round
                  class="me-edit-btn"
                  icon="el-icon-document"
                  @click="dialogVisible = true"
                  >编辑资料</el-button
                >
              </template>
              <template v-else>
                <el-button
                  v-if="!isFollow"
                  icon="el-icon-plus"
                  class="me-follow-btn"
                  type="primary"
                  size="small"
                  round
                  @click="isFollow = !isFollow"
                  >关注</el-button
                >
                <el-button
                  v-else
                  icon="el-icon-close"
                  class="me-follow-btn"
                  type="warning"
                  size="small"
                  round
                  @click="isFollow = !isFollow"
                  >取关</el-button
                >
              </template>
            </div>
            <div class="me-person-info-2">
              <ul>
                <li>8 文章</li>
                |
                <li>2 被收藏</li>
                |
                <li>3 粉丝</li>
                |
                <li>0 关注</li>
              </ul>
            </div>
          </div>
        </div>
        <div class="me-person-down">个性签名 :{{ form.status }}</div>
      </el-header>

      <!-- 主体 -->
      <el-container>
        <el-aside>
          <div class="">
            <card-article
              class="me-recommend"
              cardHeader="推荐文章"
              :articles="hotArticles"
            ></card-article>
          </div>
        </el-aside>
        <el-main class="me-articles me-area">
          <scroll-page
            :loading="loading"
            :offset="offset"
            :no-data="noData"
            v-on:load="load"
          >
            <article-item
              v-for="a in myArticles"
              :key="a.id"
              v-bind="a"
            ></article-item>
          </scroll-page>
        </el-main>
      </el-container>

      <!-- 编辑资料 -->
      <el-dialog :visible.sync="dialogVisible" width="50%">
        <!-- 个人信息 -->
        <el-card class="me-person-card">
          <el-form ref="form" :model="form" label-width="200px" size="small">
            <!-- 头像修改-待接口 -->
            <el-form-item style="text-align: center" label-width="0">
              <el-upload
                class="avatar-uploader"
                action="http://localhost:8888/upload"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
              >
                <img :src="author.avatar" v-bind="form.avatar" />
              </el-upload>
              <span>点击头像即可修改头像</span>
            </el-form-item>

            <el-form-item label="用户名" style="margin-right: 150px">
              <el-input v-model="form.account" disabled></el-input>
            </el-form-item>
            <el-form-item label="昵称" style="margin-right: 150px">
              <el-input
                v-model="form.nickname"
                :disabled="!isUpdate"
              ></el-input>
            </el-form-item>
            <el-form-item label="个性签名" style="margin-right: 150px">
              <el-input v-model="form.status" :disabled="!isUpdate"></el-input>
            </el-form-item>
          </el-form>
          <div style="text-align: center">
            <el-button
              v-if="!isUpdate"
              type="primary"
              @click="isUpdate = !isUpdate"
              >修改个人信息</el-button
            >
            <template v-else-if="isUpdate">
              <el-button type="primary" @click="updateInfo('form')"
                >保存</el-button
              >
              <el-button @click="isUpdate = !isUpdate">取消</el-button>
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
          append-to-body
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
              <el-input
                type="password"
                v-model="ruleForm.old"
                autocomplete="off"
              ></el-input>
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
      </el-dialog>
    </el-container>
  </div>
</template>


<script>
import anime from "animejs";
import { userModify, updatePassword, getUserById } from "../../api/login";
import CardArticle from "@/components/card/CardArticle";
import ArticleItem from "@/components/article/ArticleItem";
import ScrollPage from "@/components/scrollpage";
import { getHotArtices, getMyArtices } from "@/api/article";

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
      author: {}, //该主页的博主
      login: this.$store.state,
      myArticles: [],
      hotArticles: [],
      dialogVisible: false,
      page: {
        categoryId: "",
        month: "",
        page: 1,
        pageSize: 5,
        tagId: "",
        year: "",
      },
      form: {
        account: "",
        nickName: "",
        status: "",
      },
      ruleForm: {
        old: "",
        pass: "",
        checkPass: "",
      },
      isUpdate: false,
      isFollow: false,
      updatePasswordDialog: false,
      rules: {
        old: [{ required: true, message: "请输入当前密码", trigger: "blur" }],
        pass: [
          { validator: validatePass, trigger: "blur" },
          { min: 6, message: "不能少于6个字符", trigger: "blur" },
        ],
        checkPass: [
          { validator: validatePass2, trigger: "blur" },
          { min: 6, message: "不能少于6个字符", trigger: "blur" },
        ],
      },
    };
  },
  components: {
    "card-article": CardArticle,
    "article-item": ArticleItem,
    "scroll-page": ScrollPage,
  },
  mounted() {
    this.init();
  },
  methods: {
    // 列表入场动画
    listAnimate() {
      anime({
        targets: ".me-articles .me-area",
        translateX: [-20, 0],
        opacity: [0, 1],
        easing: "linear",
        duration: 150,
        delay: anime.stagger(150), // 每个元素的延迟增加150毫秒。
      });
    },
    // 获取文章
    getMyArtices() {
      getMyArtices(this.$route.params.id, this.login.token)
        .then((data) => {
          this.myArticles = data.data;
          this.$nextTick(() => {
            this.listAnimate(); //展示动画
          });
        })
        .catch((error) => {
          if (error != "error") {
            this.$message.error("加载文章失败");
          }
        });
    },
    // 获取最热文章列表
    getHotArtices() {
      getHotArtices()
        .then((data) => {
          this.hotArticles = data.data;
        })
        .catch((error) => {
          if (error !== "error") {
            this.$message({
              type: "error",
              message: "最热文章加载失败!",
              showClose: true,
            });
          }
        });
    },

    // 更新用户密码信息
    changePwd(formName) {
      this.updatePasswordDialog = true;
      // 每次刷新表单
      this.$nextTick(() => {
        this.$refs[formName].resetFields();
      });
    },
    // 修改密码
    updatePassword(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let data = {
            oldPwd: this.ruleForm.old,
            newPwd: this.ruleForm.pass,
          };
          updatePassword(data, this.login.token)
            .then((data) => {
              if (data.success) {
                this.$message.success("修改成功！");
              } else {
                this.$message.error("修改失败");
              }
              this.updatePasswordDialog = false;
            })
            .catch((error) => {
              if (error !== "error") this.$message.error("修改失败");
            });
        } else {
          this.$message.error("请输入内容！");
          return false;
        }
      });
    },
    //更新用户信息
    updateInfo(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log(this.form);
          userModify(this.form, this.login.token)
            .then(() => {
              this.$message.success("修改成功！");
            })
            .catch((error) => {
              if (error !== "error") this.$message.error("修改失败");
            });
        } else {
          this.$message.error("请输入内容！");
          return false;
        }
      });
      this.isUpdate = !this.isUpdate;
      // this.dialogVisible = false;
    },
    //获取该主页用户信息
    getUser() {
      let id = this.$route.params.id;
      getUserById(id, this.login.token)
        .then((data) => {
          if (data.success) {
            this.author = data.data;
            this.form = this.author;
            // console.log(data.data)
          } else {
            this.$message({
              type: "error",
              message: data.msg,
              showClose: true,
            });
          }
        })
        .catch((error) => {
          if (error != "error") this.$message.error("加载错误");
        });
    },

    init() {
      this.getUser();
      this.getHotArtices();
      this.getMyArtices();
    },
  },
};
</script>

<style lang="less" scoped>
.me-personal-main {
  margin: 30px 50px;
  .el-header {
    background-color: #fff;
    height: 120px !important;
    margin-bottom: 30px;
    .me-person-up {
      position: relative;
      .me-avatar-box {
        position: absolute;
        left: 15px;
        top: -30px;
        height: 80px;
        width: 80px;
        border-radius: 50%;
        padding: 5px;
        border: 1px solid #eee;
        box-shadow: 0 0 10px #ddd;
        background-color: #f6f6f6;
        img {
          width: 100%;
          height: 100%;
          border-radius: 50%;
        }
      }
      .me-person-info {
        position: absolute;
        left: 130px;
        .me-person-info-1 {
          font-size: 26px;
          padding-top: 8px;
          color: #555;
          font-family: "Gill Sans", "Gill Sans MT", Calibri, "Trebuchet MS",
            sans-serif;
          .me-follow-btn {
            position: absolute;
            top: 6px;
            right: 0px;
          }
          .me-edit-btn {
            position: absolute;
            top: 20px;
            right: -170%;
          }
        }
        .me-person-info-2 {
          padding-top: 5px;
          li {
            color: #888;
            font-family: "Gill Sans", "Gill Sans MT", Calibri, "Trebuchet MS",
              sans-serif;
            display: inline;
            padding: 0 8px;
          }
        }
      }
    }
    .me-person-down {
      padding-top: 80px;
      color: #666;
    }
  }
  .el-aside {
    background: url("../../assets/img/mybg.jpg");
    background-size: cover;
    padding: 30px 30px;
    .me-recommend {
      position: fixed;
      width: 241px;
      height: 251px;
      left: 201px;
      top: 280px;
      background-color: #fff;
      padding: 0px 30px;
    }
  }
  .me-area {
    margin-left: 3%;
    background-color: #fff;
  }
}

.el-dialog {
  .el-upload {
    img {
      height: 80px;
      width: 80px;
      border-radius: 50%;
    }
  }
}
.el-card {
  border-radius: 10px;
}

.el-card:not(:first-child) {
  margin-top: 20px;
}
</style>