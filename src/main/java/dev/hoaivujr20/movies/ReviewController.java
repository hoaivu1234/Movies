package dev.hoaivujr20.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController // Chú thích @RestController đánh dấu lớp ReviewController như một RESTful Controller. Nó cho biết rằng lớp này xử lý các yêu cầu HTTP và trả về phản hồi HTTP.
@RequestMapping("/api/v1/reviews") //tất cả các yêu cầu đến "/api/v1/reviews" sẽ được định tuyến tới lớp ReviewController.
public class ReviewController {
    @Autowired //tiêm một đối tượng ReviewService vào lớp ReviewController
    private ReviewService service;

    @PostMapping("") // phương thức này sẽ được gọi khi một yêu cầu POST được gửi đến đâ
    public ResponseEntity<Review> createReview(@RequestBody Map<String, String> payload) {
        //chấp nhận một đối tượng Map<String, String> là dữ liệu đầu vào từ yêu cầu POST. Đối tượng Map này chứa các cặp khóa/giá trị, trong trường hợp này, chúng là "reviewBody" và "imdbId"
        return new ResponseEntity<Review>(service.createReview(payload.get("reviewBody"), payload.get("imdbId")), HttpStatus.OK);
        //tạo một đối tượng ResponseEntity trả về đối tượng Review được tạo ra bởi phương thức service.createReview(...) với mã trạng thái HTTP 200 (OK)
    }
}
