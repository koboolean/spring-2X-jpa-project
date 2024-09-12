package jpabook.jpashop.service.order;

import jpabook.jpashop.domain.delivery.Delivery;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.domain.order.OrderItem;
import jpabook.jpashop.repository.item.ItemRepository;
import jpabook.jpashop.repository.member.MemberRepository;
import jpabook.jpashop.repository.order.OrderRepository;
import jpabook.jpashop.repository.order.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final MemberRepository memberRepository;

    private final ItemRepository itemRepository;

    /**
     * 주문정보 저장
     * @param memberId
     * @param itemId
     * @param count
     * @return Long
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        Member member = memberRepository.findById(memberId);
        Item item = itemRepository.findById(itemId);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        OrderItem orderItem = OrderItem.of(item, item.getPrice(), count);

        Order order = Order.of(member, delivery, orderItem);

        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 주문 취소
     * @param orderId
     */
    @Transactional
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findById(orderId);
        // 주문 취소
        order.cancel();
    }

    public List<Order> findOrder(OrderSearch orderSearch){
        return orderRepository.findAllByCriteria(orderSearch);
    }
}
