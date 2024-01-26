package Honzapda.Honzapda_server.shop.data.dto;

import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
public class SliceTotal<T> extends SliceImpl<T> {

    private static final long serialVersionUID = 867755909294344406L;
    private final Long total;

    public SliceTotal(List<T> content, Pageable pageable, boolean hasNext, Long total) {
        super(content, pageable, hasNext);
        this.total = total;
    }

    public SliceTotal(List<T> content) {
        super(content);
        this.total = (long) content.size();
    }

    public Long getTotal() {
        return total;
    }

//    @Override
//    public <U> Slice<U> map(Function<? super T, ? extends U> converter) {
//        return new SliceTotal<>(getConvertedContent(converter), pageable, hasNext, total);
//    }
}
