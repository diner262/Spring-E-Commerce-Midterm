-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Máy chủ: localhost:3306
-- Thời gian đã tạo: Th4 09, 2023 lúc 05:31 AM
-- Phiên bản máy phục vụ: 10.4.25-MariaDB
-- Phiên bản PHP: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `ecommerce_db`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(30);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `items`
--

CREATE TABLE `items` (
  `id` bigint(20) NOT NULL,
  `quantity` int(11) NOT NULL,
  `total_price` double NOT NULL,
  `product_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `orders`
--

CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL,
  `date` date DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `total_price` double NOT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `orders`
--

INSERT INTO `orders` (`id`, `date`, `status`, `total_price`, `user_id`) VALUES
(2, '2023-04-08', 'Progressing', 89980000, 8),
(3, '2023-04-08', 'Pending Payment', 31470000, 8),
(4, '2023-04-08', 'Cancelled', 60470000, 14),
(5, '2023-04-08', 'Completed', 50990000, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `orders_order_detail`
--

CREATE TABLE `orders_order_detail` (
  `order_id` bigint(20) NOT NULL,
  `order_detail_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `orders_order_detail`
--

INSERT INTO `orders_order_detail` (`order_id`, `order_detail_id`) VALUES
(2, 2),
(2, 3),
(3, 4),
(4, 5),
(4, 6),
(5, 7);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `order_detail`
--

CREATE TABLE `order_detail` (
  `id` bigint(20) NOT NULL,
  `quantity` int(11) NOT NULL,
  `total_price` double NOT NULL,
  `product_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `order_detail`
--

INSERT INTO `order_detail` (`id`, `quantity`, `total_price`, `product_id`) VALUES
(2, 1, 50990000, 1),
(3, 1, 38990000, 6),
(4, 3, 10490000, 4),
(5, 2, 21490000, 7),
(6, 1, 17490000, 2),
(7, 1, 50990000, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `products`
--

CREATE TABLE `products` (
  `id` bigint(20) NOT NULL,
  `brand` varchar(255) DEFAULT NULL,
  `graphic_card` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `processor` varchar(255) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `ram` int(11) DEFAULT NULL,
  `rom` int(11) DEFAULT NULL,
  `screen_size` double DEFAULT NULL,
  `weight` double DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `products`
--

INSERT INTO `products` (`id`, `brand`, `graphic_card`, `image`, `name`, `price`, `processor`, `quantity`, `ram`, `rom`, `screen_size`, `weight`) VALUES
(1, 'Apple', 'Apple M1', 'MacBook Pro 14 2023 M2 Pro.jpeg', 'MacBook Pro 14 2023 M2 Pro', 50990000, 'Apple, M2, 10-Core', 100, 16, 512, 14, 1.6),
(2, 'Asus', 'NVIDIA GeForce GTX 1650', 'Laptop Asus TUF Gaming.png', 'Laptop Asus TUF Gaming', 17490000, 'Intel, Core i5, 10300H', 244, 8, 512, 15.6, 2.3),
(3, 'Acer', 'NVIDIA GeForce RTX 3050', 'Laptop Acer Nitro Gaming.png', 'Laptop Acer Nitro Gaming', 19990000, 'Intel, Core i5, 11400H', 111, 8, 512, 15.6, 2.2),
(4, 'HP', 'Intel UHD Graphics', 'Laptop HP 15s.png', 'Laptop HP 15s', 10490000, 'Intel, Core i3, 1115G4', 26, 8, 256, 15.6, 1.7),
(5, 'HP', ' NVIDIA GeForce RTX 3050 Ti', 'Laptop HP Gaming Victus.png', 'Laptop HP Gaming Victus', 23990000, 'Intel, Core i7, 11800H', 16, 8, 512, 17, 2.4),
(6, 'Apple', 'Apple M2 GPU 10 nhân', 'MacBook Pro M2 2022 13.png', 'MacBook Pro M2 2022 13', 38990000, ' Apple, M2, 8 - Core', 55, 16, 512, 13.3, 1.4),
(7, 'Asus', 'NVIDIA GeForce RTX 3050', 'Laptop Asus Gaming ROG.png', 'Laptop Asus Gaming ROG', 21490000, ' AMD, Ryzen 7, 4800H', 312, 8, 512, 15.6, 2.1),
(8, 'Acer', 'Intel UHD Graphics', 'Acer Aspire 5 A514.png', 'Acer Aspire 5 A514', 8490000, ' Intel, Core i3, 1005G1', 76, 8, 512, 14, 1.5),
(9, 'Apple', 'Apple M1', 'MacBook Pro 16 2021 M1 Pro.png', 'MacBook Pro 16 2021 M1 Pro', 63990000, 'Apple, M1 Pro', 46, 32, 512, 16.2, 2.1),
(11, 'Asus', 'AMD Radeon Graphics', 'Laptop Asus Vivobook Pro.png', 'Laptop Asus Vivobook Pro', 17990000, 'AMD, Ryzen 7, 5800H', 233, 8, 512, 14, 1.4),
(12, 'Dell', 'Intel Iris Xe Graphics', 'Laptop Dell Vostro V5620.png', 'Laptop Dell Vostro V5620', 30490000, 'Intel, Core i7, 1240P', 88, 16, 512, 15.6, 1.9),
(13, 'MSI', ' NVIDIA GeForce RTX 3050', 'Laptop Gigabyte Gaming G5.png', 'Laptop Gigabyte Gaming G5', 25890000, 'Intel, Core i5, 12500H', 234, 16, 512, 15.6, 1.9),
(14, 'Asus', '8 GB, DDR4, 3200 MHz', 'Laptop Asus Vivobook M513UA.png', 'Laptop Asus Vivobook M513UA', 15490000, 'AMD, Ryzen 7, 5700U', 523, 8, 512, 15.6, 1.8),
(15, 'Apple', ' Apple M1', 'MacBook Pro 16 2021 M1 Pro.png', 'MacBook Pro 16 2021 M1 Pro', 56990000, ' Apple, M1 Pro', 10, 16, 1024, 16.2, 2.1),
(16, 'Asus', 'NVIDIA GeForce RTX 3050', 'Laptop Asus Gaming Zephyrus.png', 'Laptop Asus Gaming Zephyrus', 26990000, 'AMD, Ryzen 7, 5800HS', 42, 8, 512, 15.6, 1.6),
(17, 'Dell', 'NVIDIA GeForce RTX 3050 Ti', 'Laptop Dell XPS 15 9520.png', 'Laptop Dell XPS 15 9520', 76990000, ' Intel, Core i9, 12900HK', 0, 16, 512, 15.6, 2),
(18, 'HP', 'NVIDIA GeForce GTX 1650 Ti', 'Laptop HP Pavilion Gaming 15.png', 'Laptop HP Pavilion Gaming 15', 27990000, 'Intel, Core i7, 10750H', 101, 8, 512, 15.6, 2.2),
(19, 'Lenovo', 'AMD Radeon 610 2GB', 'Laptop Lenovo IdeaPad.png', 'Laptop Lenovo IdeaPad', 11490000, 'AMD, Ryzen 5, 7520U', 219, 8, 256, 15.6, 1.6);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `roles`
--

CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `roles`
--

INSERT INTO `roles` (`id`, `name`) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `shopping_cart`
--

CREATE TABLE `shopping_cart` (
  `id` bigint(20) NOT NULL,
  `token_session` varchar(255) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `shopping_cart`
--

INSERT INTO `shopping_cart` (`id`, `token_session`, `product_id`) VALUES
(26, '1ff119cc-714e-4e23-a414-d4b87cb079d8', NULL),
(28, 'e7d6f6fa-5182-476e-9dd9-6d01dfcd4ba1', NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `shopping_cart_cart_items`
--

CREATE TABLE `shopping_cart_cart_items` (
  `shopping_cart_id` bigint(20) NOT NULL,
  `cart_items_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`id`, `address`, `age`, `email`, `gender`, `password`, `phone`, `username`) VALUES
(1, '19, Nguyen Huu Tho Str., Tan Phong ward, District 7, Ho Chi Minh City', 23, 'admin@gmail.com', 'male', '$2a$10$8Lm0EAyrN75DSfUUEXDe6uR0qEq4JxtjT0S.Y0gjOtrF8BkJZyycG', '0987654321', 'admin'),
(8, '14 Street 6, District 7, HCM city', 22, 'din262bmt@gmail.com', 'male', '$2a$10$tL3561ZCiuCrLQtT/Dr2iexAYR993TqWW2iNU1T1NFdvlq5M9knN2', '0944217297', 'din'),
(14, '', 20, 'baokhoi262@gmail.com', 'female', '$2a$10$XJ7Fc15GlggTv31gjcCyPOjK7bt.iki4ixAo5sp0r/sJOEdCZ8ivK', '0364231412', 'khoi'),
(20, '', 13, 'admin1@gmail.com', 'female', '$2a$10$UVAgPRWUlvykGRyN73m71uuKWTP2D.SGpmQsY6buxPuA9nCqMAfcC', '0944217297', 'admin1');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users_roles`
--

CREATE TABLE `users_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `users_roles`
--

INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES
(1, 1),
(8, 2),
(14, 2),
(20, 2);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `items`
--
ALTER TABLE `items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKmtk37pxnx7d5ck7fkq2xcna4i` (`product_id`);

--
-- Chỉ mục cho bảng `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK32ql8ubntj5uh44ph9659tiih` (`user_id`);

--
-- Chỉ mục cho bảng `orders_order_detail`
--
ALTER TABLE `orders_order_detail`
  ADD UNIQUE KEY `UK_g1w92taktolhmtwevabujl42m` (`order_detail_id`),
  ADD KEY `FKc5qtkfypfmgrfleg27emcclnk` (`order_id`);

--
-- Chỉ mục cho bảng `order_detail`
--
ALTER TABLE `order_detail`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKc7q42e9tu0hslx6w4wxgomhvn` (`product_id`);

--
-- Chỉ mục cho bảng `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `shopping_cart`
--
ALTER TABLE `shopping_cart`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK26ajdolmyw3a95bhn6pjk5dor` (`product_id`);

--
-- Chỉ mục cho bảng `shopping_cart_cart_items`
--
ALTER TABLE `shopping_cart_cart_items`
  ADD UNIQUE KEY `UK_kfrn31bu4vp09qlx5j1q21x86` (`cart_items_id`),
  ADD KEY `FKpocjoqhr41wij71udgwfqbuiv` (`shopping_cart_id`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `users_roles`
--
ALTER TABLE `users_roles`
  ADD KEY `FKj6m8fwv7oqv74fcehir1a9ffy` (`role_id`),
  ADD KEY `FK2o0jvgh89lemvvo17cbqvdxaa` (`user_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `orders`
--
ALTER TABLE `orders`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `order_detail`
--
ALTER TABLE `order_detail`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT cho bảng `products`
--
ALTER TABLE `products`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT cho bảng `roles`
--
ALTER TABLE `roles`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
