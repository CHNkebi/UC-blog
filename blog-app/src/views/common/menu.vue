<template>
  <div>
    <scroll-page
      :loading="loading"
      :offset="offset"
      :no-data="noData"
      v-on:load="load"
    >
      <article-item v-for="a in articles" :key="a.id" v-bind="a">
        <span class="me-aticle-delete">
          <el-button
            @click="deleteArticle(a)"
            type="danger"
            icon="el-icon-delete"
            size="mini"
            circle
          ></el-button>
        </span>
      </article-item>
    </scroll-page>
  </div>
</template>

<script>
import ArticleItem from "@/components/article/ArticleItem";
import ScrollPage from "@/components/scrollpage";
import { getMyArchives } from "../../api/article";

export default {
  name: "myMenu",
  props: {
    offset: {
      type: Number,
      default: 100,
    },
    page: {
      type: Object,
      default() {
        return {};
      },
    },
    query: {
      type: Object,
      default() {
        return {};
      },
    },
  },
  watch: {
    query: {
      handler() {
        this.noData = false;
        this.articles = [];
        this.innerPage.pageNumber = 1;
        this.getArticles();
      },
      deep: true,
    },
    page: {
      handler() {
        this.noData = false;
        this.articles = [];
        this.innerPage = this.page;
        this.getArticles();
      },
      deep: true,
    },
  },
  created() {
    this.init();
    this.getArticles();
  },
  data() {
    return {
      authorId: "",
      loading: false,
      noData: false,
      innerPage: {
        pageSize: 5,
        pageNumber: 1,
      },
      articles: [],
    };
  },
  methods: {
    load() {
      this.getArticles();
    },
    init() {
      // 加载用户缓存
      this.authorId = this.$store.state.id;
    },
    view(id) {
      this.$router.push({ path: `/view/${id}` });
    },
    getArticles() {
      this.loading = true;

      getMyArchives(this.query, this.innerPage)
        .then((res) => {
          let newArticles = res.data;
          if (newArticles && newArticles.length > 0) {
            this.innerPage.pageNumber += 1;
            for (var i in newArticles) {
              if (newArticles[i].author.id == this.authorId) {
                // console.log(newArticles[i])
                this.articles.push(newArticles[i]);
              }
            }
          } else {
            this.noData = true;
          }
        })
        .catch((error) => {
          if (error !== "error") {
            this.$message({
              type: "error",
              message: "您暂无已发表博客",
              showClose: true,
            });
          }
        })
        .finally(() => {
          this.loading = false;
        });
    },

    deleteArticle(archive) {
      //删除博客-待接口
      console.log(archive);
    },
  },
  components: {
    "article-item": ArticleItem,
    "scroll-page": ScrollPage,
  },
};
</script>

<style scoped>
.el-card {
  border-radius: 0;
}

.el-card:not(:first-child) {
  margin-top: 10px;
}

#scroll-page {
  padding: 0 30px;
  width: 800px;
}

.me-aticle-delete {
  float: right;
}
</style>
