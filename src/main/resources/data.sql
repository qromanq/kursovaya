-- Тестовые данные для системы управления запасами
INSERT INTO products (name, category, quantity, price, supplier, min_stock, description, barcode, location, status, created_at, updated_at) VALUES 
('Ноутбук Dell Inspiron 15', 'Компьютеры', 5, 45000.00, 'Dell Technologies', 3, 'Ноутбук для офисной работы с процессором Intel Core i5', '1234567890123', 'Склад А-1', 'IN_STOCK', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Мышь Logitech MX Master 3', 'Периферия', 20, 1500.00, 'Logitech', 10, 'Беспроводная мышь для профессионалов', '2345678901234', 'Склад А-2', 'IN_STOCK', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Клавиатура механическая Keychron K2', 'Периферия', 15, 2500.00, 'Keychron', 8, 'Механическая клавиатура с подсветкой', '3456789012345', 'Склад А-2', 'IN_STOCK', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Монитор Samsung 24" Full HD', 'Мониторы', 8, 15000.00, 'Samsung Electronics', 5, 'IPS монитор 24 дюйма с разрешением 1920x1080', '4567890123456', 'Склад Б-1', 'IN_STOCK', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Принтер HP LaserJet Pro', 'Оргтехника', 3, 12000.00, 'HP Inc.', 2, 'Лазерный принтер для офиса', '5678901234567', 'Склад Б-2', 'IN_STOCK', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Веб-камера Logitech C920', 'Периферия', 12, 3500.00, 'Logitech', 6, 'HD веб-камера для видеоконференций', '6789012345678', 'Склад А-2', 'IN_STOCK', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Наушники Sony WH-1000XM4', 'Аудио', 25, 4500.00, 'Sony Corporation', 15, 'Беспроводные наушники с шумоподавлением', '7890123456789', 'Склад В-1', 'IN_STOCK', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Планшет iPad Air', 'Планшеты', 7, 35000.00, 'Apple Inc.', 4, 'Планшет Apple iPad Air с дисплеем 10.9 дюймов', '8901234567890', 'Склад В-2', 'IN_STOCK', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Смартфон Samsung Galaxy S23', 'Смартфоны', 10, 25000.00, 'Samsung Electronics', 6, 'Флагманский смартфон с камерой 50 МП', '9012345678901', 'Склад В-2', 'IN_STOCK', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Роутер TP-Link Archer AX73', 'Сетевое оборудование', 6, 2800.00, 'TP-Link', 4, 'Wi-Fi 6 роутер для дома и офиса', '0123456789012', 'Склад Б-3', 'IN_STOCK', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('SSD Samsung 970 EVO Plus 1TB', 'Комплектующие', 2, 8500.00, 'Samsung Electronics', 5, 'Твердотельный накопитель M.2 NVMe', '1357924680135', 'Склад А-3', 'IN_STOCK', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Оперативная память Corsair 16GB', 'Комплектующие', 1, 4200.00, 'Corsair', 8, 'DDR4-3200 16GB (2x8GB) для игровых ПК', '2468135792468', 'Склад А-3', 'IN_STOCK', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Видеокарта NVIDIA RTX 4060', 'Комплектующие', 0, 32000.00, 'NVIDIA Corporation', 3, 'Игровая видеокарта среднего класса', '3691472583691', 'Склад А-3', 'OUT_OF_STOCK', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Кабель USB-C 2м', 'Аксессуары', 50, 350.00, 'Belkin', 25, 'Кабель для зарядки и передачи данных', '4815926374815', 'Склад Г-1', 'IN_STOCK', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Подставка для ноутбука', 'Аксессуары', 18, 1200.00, 'Rain Design', 10, 'Алюминиевая подставка с охлаждением', '5927384615927', 'Склад Г-1', 'IN_STOCK', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);