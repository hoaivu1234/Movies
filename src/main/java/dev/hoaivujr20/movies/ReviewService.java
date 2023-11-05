package dev.hoaivujr20.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service //đánh dấu lớp ReviewService như một dịch vụ trong Spring Framework. Dịch vụ này thường được sử dụng để bao gồm logic kinh doanh và xử lý dữ liệu.
public class ReviewService {
    @Autowired
    private ReviewRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Review createReview(String reviewBody, String imdbId) {
        Review review = repository.insert(new Review(reviewBody, LocalDateTime.now(), LocalDateTime.now()));
        //tạo một đánh giá mới và liên kết nó với một bộ phim cụ thể.
        mongoTemplate.update(Movie.class)
                // Dòng mã này nói với MongoDB rằng bạn muốn thực hiện một thao tác cập nhật trên bảng "movies".
                .matching(Criteria.where("imdbId").is(imdbId))
                // Nó xác định rằng bạn muốn cập nhật bản ghi trong bảng "movies" mà có trường "imdbId" khớp với giá trị imdbId được truyền vào phương thức createReview.
                .apply(new Update().push("reviews").value(review))
                //Nó cho biết rằng bạn muốn thêm (push) một giá trị vào mảng (danh sách) "reviews" của bản ghi bộ phim được tìm thấy bằng câu truy vấn trước đó
                .first();//.first() nói với MongoDB rằng bạn muốn áp dụng thao tác cập nhật lên bản ghi đầu tiên khớp với câu truy vấn.

        return review;
        // thực hiện việc tìm kiếm bộ phim thông qua id sau đó thêm đánh giá vào bảng review của bộ phim đó
    }
}