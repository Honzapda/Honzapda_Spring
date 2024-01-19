package Honzapda.Honzapda_server.shop.data;

import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.shop.data.entity.ShopUserBookmark;
import Honzapda.Honzapda_server.user.data.entity.User;

public class BookmarkConverter {

    public static ShopUserBookmark toBookmark(User user, Shop shop) {
        return ShopUserBookmark.builder()
                .user(user)
                .shop(shop)
                .build();
    }
}
