// JavaScript для системы управления запасами
const API_URL = '/api/products';

// Добавление товара
document.getElementById('productForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const productData = {
        name: document.getElementById('productName').value,
        category: document.getElementById('productCategory').value || null,
        quantity: parseInt(document.getElementById('productQuantity').value),
        price: parseFloat(document.getElementById('productPrice').value),
        supplier: document.getElementById('productSupplier').value || null,
        minStock: parseInt(document.getElementById('productMinStock').value) || 5,
        barcode: document.getElementById('productBarcode').value || null,
        location: document.getElementById('productLocation').value || null,
        description: document.getElementById('productDescription').value || null,
        status: 'IN_STOCK'
    };

    try {
        const response = await fetch(API_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(productData)
        });

        if (response.ok) {
            showMessage('Товар успешно добавлен', 'success');
            document.getElementById('productForm').reset();
            setTimeout(() => location.reload(), 1000);
        } else {
            showMessage('Ошибка добавления товара', 'error');
        }
    } catch (error) {
        showMessage('Ошибка: ' + error.message, 'error');
    }
});

// Поиск товаров
async function searchProducts() {
    const searchTerm = document.getElementById('searchInput').value;
    if (!searchTerm.trim()) {
        showAllProducts();
        return;
    }

    try {
        const response = await fetch(`${API_URL}/search?name=${encodeURIComponent(searchTerm)}`);
        const products = await response.json();
        updateProductsTable(products);
    } catch (error) {
        showMessage('Ошибка поиска: ' + error.message, 'error');
    }
}

// Показать все товары
function showAllProducts() {
    location.reload();
}

// Показать товары с низким запасом
async function showLowStock() {
    try {
        const response = await fetch(`${API_URL}/low-stock`);
        const products = await response.json();
        updateProductsTable(products);
        showMessage(`Найдено ${products.length} товаров с низким запасом`, 'info');
    } catch (error) {
        showMessage('Ошибка: ' + error.message, 'error');
    }
}

// Показать товары без запаса
async function showOutOfStock() {
    try {
        const response = await fetch(`${API_URL}/out-of-stock`);
        const products = await response.json();
        updateProductsTable(products);
        showMessage(`Найдено ${products.length} товаров без запаса`, 'info');
    } catch (error) {
        showMessage('Ошибка: ' + error.message, 'error');
    }
}

// Показать топ товаров по стоимости
async function showTopValue() {
    try {
        const response = await fetch(`${API_URL}/top-value`);
        const products = await response.json();
        updateProductsTable(products.slice(0, 10)); // Топ 10
        showMessage('Показаны топ 10 товаров по стоимости', 'info');
    } catch (error) {
        showMessage('Ошибка: ' + error.message, 'error');
    }
}

// Фильтрация по категории
async function filterByCategory() {
    const category = document.getElementById('categoryFilter').value;
    if (!category) {
        showAllProducts();
        return;
    }

    try {
        const response = await fetch(`${API_URL}/category/${encodeURIComponent(category)}`);
        const products = await response.json();
        updateProductsTable(products);
        showMessage(`Показаны товары категории: ${category}`, 'info');
    } catch (error) {
        showMessage('Ошибка фильтрации: ' + error.message, 'error');
    }
}

// Фильтрация по поставщику
async function filterBySupplier() {
    const supplier = document.getElementById('supplierFilter').value;
    if (!supplier) {
        showAllProducts();
        return;
    }

    try {
        const response = await fetch(`${API_URL}/supplier?supplier=${encodeURIComponent(supplier)}`);
        const products = await response.json();
        updateProductsTable(products);
        showMessage(`Показаны товары поставщика: ${supplier}`, 'info');
    } catch (error) {
        showMessage('Ошибка фильтрации: ' + error.message, 'error');
    }
}

// Фильтрация по статусу
async function filterByStatus() {
    const status = document.getElementById('statusFilter').value;
    if (!status) {
        showAllProducts();
        return;
    }

    try {
        const response = await fetch(`${API_URL}/status/${status}`);
        const products = await response.json();
        updateProductsTable(products);
        showMessage(`Показаны товары со статусом: ${status}`, 'info');
    } catch (error) {
        showMessage('Ошибка фильтрации: ' + error.message, 'error');
    }
}

// Сброс фильтров
function clearFilters() {
    document.getElementById('categoryFilter').value = '';
    document.getElementById('supplierFilter').value = '';
    document.getElementById('statusFilter').value = '';
    document.getElementById('searchInput').value = '';
    showAllProducts();
}

// Добавить к запасу
async function addStock(id) {
    const quantity = prompt('Количество для добавления:');
    if (quantity === null || isNaN(quantity) || quantity <= 0) return;

    try {
        const response = await fetch(`${API_URL}/${id}/add-stock?quantity=${quantity}`, {
            method: 'PATCH'
        });

        if (response.ok) {
            showMessage('Запас успешно пополнен', 'success');
            setTimeout(() => location.reload(), 1000);
        } else {
            showMessage('Ошибка пополнения запаса', 'error');
        }
    } catch (error) {
        showMessage('Ошибка: ' + error.message, 'error');
    }
}

// Убрать из запаса
async function removeStock(id) {
    const quantity = prompt('Количество для списания:');
    if (quantity === null || isNaN(quantity) || quantity <= 0) return;

    try {
        const response = await fetch(`${API_URL}/${id}/remove-stock?quantity=${quantity}`, {
            method: 'PATCH'
        });

        if (response.ok) {
            showMessage('Товар успешно списан', 'success');
            setTimeout(() => location.reload(), 1000);
        } else {
            showMessage('Ошибка списания товара', 'error');
        }
    } catch (error) {
        showMessage('Ошибка: ' + error.message, 'error');
    }
}

// Редактирование товара
async function editProduct(id) {
    try {
        const response = await fetch(`${API_URL}/${id}`);
        const product = await response.json();
        
        // Заполняем форму редактирования
        document.getElementById('editId').value = product.id;
        document.getElementById('editName').value = product.name;
        document.getElementById('editCategory').value = product.category || '';
        document.getElementById('editQuantity').value = product.quantity;
        document.getElementById('editPrice').value = product.price;
        document.getElementById('editSupplier').value = product.supplier || '';
        document.getElementById('editMinStock').value = product.minStock || 5;
        document.getElementById('editBarcode').value = product.barcode || '';
        document.getElementById('editLocation').value = product.location || '';
        document.getElementById('editDescription').value = product.description || '';
        document.getElementById('editStatus').value = product.status || 'ACTIVE';
        
        // Показываем модальное окно
        document.getElementById('editModal').style.display = 'block';
    } catch (error) {
        showMessage('Ошибка загрузки товара: ' + error.message, 'error');
    }
}

// Сохранение изменений товара
document.getElementById('editForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const id = document.getElementById('editId').value;
    const productData = {
        name: document.getElementById('editName').value,
        category: document.getElementById('editCategory').value || null,
        quantity: parseInt(document.getElementById('editQuantity').value),
        price: parseFloat(document.getElementById('editPrice').value),
        supplier: document.getElementById('editSupplier').value || null,
        minStock: parseInt(document.getElementById('editMinStock').value) || 5,
        barcode: document.getElementById('editBarcode').value || null,
        location: document.getElementById('editLocation').value || null,
        description: document.getElementById('editDescription').value || null,
        status: document.getElementById('editStatus').value
    };

    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(productData)
        });

        if (response.ok) {
            showMessage('Товар успешно обновлен', 'success');
            closeEditModal();
            setTimeout(() => location.reload(), 1000);
        } else {
            showMessage('Ошибка обновления товара', 'error');
        }
    } catch (error) {
        showMessage('Ошибка: ' + error.message, 'error');
    }
});

// Закрытие модального окна
function closeEditModal() {
    document.getElementById('editModal').style.display = 'none';
}

// Удаление товара
async function deleteProduct(id) {
    if (!confirm('Вы уверены, что хотите удалить этот товар?')) {
        return;
    }

    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            showMessage('Товар успешно удален', 'success');
            setTimeout(() => location.reload(), 1000);
        } else {
            showMessage('Ошибка удаления товара', 'error');
        }
    } catch (error) {
        showMessage('Ошибка: ' + error.message, 'error');
    }
}

// Обновление таблицы товаров
function updateProductsTable(products) {
    const tbody = document.getElementById('productsBody');
    tbody.innerHTML = '';

    if (products.length === 0) {
        tbody.innerHTML = '<tr><td colspan="8" style="text-align: center;">Товары не найдены</td></tr>';
        return;
    }

    products.forEach(product => {
        const row = document.createElement('tr');
        
        // Определяем класс для строки
        let rowClass = '';
        if (product.quantity < (product.minStock || 5)) {
            rowClass = 'low-stock';
        } else if (product.quantity <= 0) {
            rowClass = 'out-of-stock';
        }
        row.className = rowClass;
        
        // Определяем класс статуса
        const statusClass = `status-${product.status.toLowerCase().replace('_', '-')}`;
        
        row.innerHTML = `
            <td>${product.id}</td>
            <td>${product.name}</td>
            <td>${product.category || 'Не указана'}</td>
            <td>${product.quantity}</td>
            <td>${product.price.toFixed(2)} ₽</td>
            <td>${product.supplier || 'Не указан'}</td>
            <td><span class="${statusClass}">${getStatusText(product.status)}</span></td>
            <td>
                <button class="btn-small btn-success" onclick="addStock(${product.id})">➕</button>
                <button class="btn-small btn-warning" onclick="removeStock(${product.id})">➖</button>
                <button class="btn-small btn-warning" onclick="editProduct(${product.id})">✏️</button>
                <button class="btn-small btn-danger" onclick="deleteProduct(${product.id})">🗑️</button>
            </td>
        `;
        tbody.appendChild(row);
    });
}

// Показ сообщений
function showMessage(message, type) {
    // Удаляем существующие сообщения
    const existingMessage = document.querySelector('.message');
    if (existingMessage) {
        existingMessage.remove();
    }

    const messageDiv = document.createElement('div');
    messageDiv.className = `message message-${type}`;
    messageDiv.textContent = message;
    
    // Стили для сообщений
    messageDiv.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        padding: 1rem 2rem;
        border-radius: 5px;
        color: white;
        font-weight: bold;
        z-index: 1000;
        animation: slideIn 0.3s ease-out;
    `;
    
    // Цвета в зависимости от типа
    switch (type) {
        case 'success':
            messageDiv.style.backgroundColor = '#27ae60';
            break;
        case 'error':
            messageDiv.style.backgroundColor = '#e74c3c';
            break;
        case 'info':
            messageDiv.style.backgroundColor = '#3498db';
            break;
        default:
            messageDiv.style.backgroundColor = '#95a5a6';
    }
    
    document.body.appendChild(messageDiv);
    
    // Автоматически удаляем через 3 секунды
    setTimeout(() => {
        messageDiv.remove();
    }, 3000);
}

// Поиск по Enter
document.getElementById('searchInput').addEventListener('keypress', (e) => {
    if (e.key === 'Enter') {
        searchProducts();
    }
});

// Закрытие модального окна по клику вне его
window.onclick = function(event) {
    const modal = document.getElementById('editModal');
    if (event.target === modal) {
        closeEditModal();
    }
}

// Добавляем CSS анимацию
const style = document.createElement('style');
style.textContent = `
    @keyframes slideIn {
        from {
            transform: translateX(100%);
            opacity: 0;
        }
        to {
            transform: translateX(0);
            opacity: 1;
        }
    }
`;
document.head.appendChild(style);

// Функция для перевода статусов
function getStatusText(status) {
    switch (status) {
        case 'IN_STOCK':
            return 'В наличии';
        case 'DISCONTINUED':
            return 'Снят с производства';
        case 'OUT_OF_STOCK':
            return 'Нет в наличии';
        default:
            return status;
    }
}