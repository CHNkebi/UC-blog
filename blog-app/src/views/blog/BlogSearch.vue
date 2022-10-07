<template>
  <div>
    <h2>搜索结果</h2>
    <div class="me-articles">
      <scroll-page
        :loading="loading"
        :offset="offset"
        :no-data="noData"
        v-on:load="load"
      >
        <article-item
          v-for="a in articles"
          :key="a.id"
          v-bind="a"
        ></article-item>
      </scroll-page>
    </div>
  </div>
</template>

<script>
import anime from "animejs";
import ArticleItem from "@/components/article/ArticleItem";
import ScrollPage from "@/components/scrollpage";
import { searchArticle } from "@/api/article";

export default {
  name: "BlogSearch",
  components: {
    "article-item": ArticleItem,
    "scroll-page": ScrollPage,
  },
  data() {
    return {
      articles: {
        query: {},
      },
      search: this.$route.query.search,
    };
  },
  mounted() {
    this.load();
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
    load() {
      console.log(this.search);
      let search = this.search;
      searchArticle(search)
        .then((res) => {
          this.articles = res.data;
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
  },
};
</script>

<style scoped>
h2 {
  text-align: center;
  margin-bottom: 10px;
  font-family: "Times New Roman", Times, serif;
}
.me-articles {
  margin: 0 100px
}
.el-card {
  border-radius: 10px;
}

.el-card:not(:first-child) {
  margin-top: 20px;
}
</style>