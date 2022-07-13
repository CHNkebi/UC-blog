import com.ty.dao.ArticleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

public class main {

   @Autowired
   ArticleMapper articleMapper;

   @Test
   public void test() {
      System.out.println(articleMapper.selectById(1));
   }
}
