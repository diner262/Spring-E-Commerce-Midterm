package tdtu.edu.vn.midterm.service;

import org.springframework.stereotype.Service;
import tdtu.edu.vn.midterm.model.OrderDetail;

@Service
public interface OrderDetailService {
    OrderDetail save(OrderDetail orderDetail);
}
