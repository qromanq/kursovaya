// JavaScript для управления запасами
const API_URL = '/api/products';

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
        updateInventoryTable(products);
        showMessage(`Найдено ${products.length} товаров`, 'info');
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
        updateInventoryTable(products);
        showMessage(`Найдено ${products.length} товаров с низким запасом`, 'warning');
    } catch (error) {
        showMessage('Ошибка: ' + error.message, 'error');
    }
}

// Показать товары без запаса
async function showOutOfStock() {
    try {
        const response = await fetch(`${API_URL}/out-of-stock`);
        const products = await response.json();
        updateInventoryTable(products);
        showMessage(`Найдено ${products.length} товаров без запаса`, 'error');
    } catch (error) {
        showMessage('Ошибка: ' + error.message, 'error');
    }
}

// Быстрое добавление к запасу
async function quickAddStock(productId) {
    const quantityInput = document.getElementById(`add-${productId}`);
    const quantity = parseInt(quantityInput.value);
    
    if (!quantity || quantity <= 0) {
        showMessage('Введите корректное количество', 'error');
        return;
    }

    try {
        const response = await fetch(`${API_URL}/${productId}/add-stock?quantity=${quantity}`, {
            method: 'PATCH'
        });

        if (response.ok) {
            showMessage(`Добавлено ${quantity} единиц`, 'success');
            quantityInput.value = 1;
            setTimeout(() => location.reload(), 1000);
        } else {
            showMessage('Ошибка пополнения запаса', 'error');
        }
    } catch (error) {
        showMessage('Ошибка: ' + error.message, 'error');
    }
}

// Быстрое списание из запаса
async function quickRemoveStock(productId) {
    const quantityInput = document.getElementById(`add-${productId}`);
    const quantity = parseInt(quantityInput.value);
    
    if (!quantity || quantity <= 0) {
        showMessage('Введите корректное количество', 'error');
        return;
    }

    if (!confirm(`Списать ${quantity} единиц товара?`)) {
        return;
    }

    try {
        const response = await fetch(`${API_URL}/${productId}/remove-stock?quantity=${quantity}`, {
            method: 'PATCH'
        });

        if (response.ok) {
            showMessage(`Списано ${quantity} единиц`, 'success');
            quantityInput.value = 1;
            setTimeout(() => location.reload(), 1000);
        } else {
            showMessage('Ошибка списания товара', 'error');
        }
    } catch (error) {
        showMessage('Ошибка: ' + error.message, 'error');
    }
}

// Редактирование товара (переход на главную страницу)
function editProduct(productId) {
    window.location.href = `/?edit=${productId}`;
}

// Удаление товара
async function deleteProduct(productId) {
    if (!confirm('Вы уверены, что хотите удалить этот товар?')) {
        return;
    }

    try {
        const response = await fetch(`${API_URL}/${productId}`, {
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

// Выбор всех товаров
function toggleSelectAll() {
    const selectAll = document.getElementById('selectAll');
    const checkboxes = document.querySelectorAll('.product-checkbox');
    
    checkboxes.forEach(checkbox => {
        checkbox.checked = selectAll.checked;
    });
}

// Получить выбранные товары
function getSelectedProducts() {
    const checkboxes = document.querySelectorAll('.product-checkbox:checked');
    return Array.from(checkboxes).map(cb => parseInt(cb.value));
}

// Показать модальное окно массового пополнения
function showBulkAddModal() {
    const selected = getSelectedProducts();
    if (selected.length === 0) {
        showMessage('Выберите товары для пополнения', 'warning');
        return;
    }
    document.getElementById('bulkAddModal').style.display = 'block';
}

// Закрыть модальное окно массового пополнения
function closeBulkAddModal() {
    document.getElementById('bulkAddModal').style.display = 'none';
}

// Выполнить массовое пополнение
async function executeBulkAdd() {
    const selected = getSelectedProducts();
    const quantity = parseInt(document.getElementById('bulkAddQuantity').value);
    
    if (!quantity || quantity <= 0) {
        showMessage('Введите корректное количество', 'error');
        return;
    }

    if (selected.length === 0) {
        showMessage('Выберите товары для пополнения', 'warning');
        return;
    }

    try {
        const promises = selected.map(productId => 
            fetch(`${API_URL}/${productId}/add-stock?quantity=${quantity}`, {
                method: 'PATCH'
            })
        );

        const results = await Promise.all(promises);
        const successful = results.filter(r => r.ok).length;
        
        showMessage(`Пополнено ${successful} из ${selected.length} товаров`, 'success');
        closeBulkAddModal();
        setTimeout(() => location.reload(), 1000);
    } catch (error) {
        showMessage('Ошибка массового пополнения: ' + error.message, 'error');
    }
}

// Показать модальное окно массового списания
function showBulkRemoveModal() {
    const selected = getSelectedProducts();
    if (selected.length === 0) {
        showMessage('Выберите товары для списания', 'warning');
        return;
    }
    document.getElementById('bulkRemoveModal').style.display = 'block';
}

// Закрыть модальное окно массового списания
function closeBulkRemoveModal() {
    document.getElementById('bulkRemoveModal').style.display = 'none';
}

// Выполнить массовое списание
async function executeBulkRemove() {
    const selected = getSelectedProducts();
    const quantity = parseInt(document.getElementById('bulkRemoveQuantity').value);
    
    if (!quantity || quantity <= 0) {
        showMessage('Введите корректное количество', 'error');
        return;
    }

    if (selected.length === 0) {
        showMessage('Выберите товары для списания', 'warning');
        return;
    }

    if (!confirm(`Списать ${quantity} единиц с ${selected.length} товаров?`)) {
        return;
    }

    try {
        const promises = selected.map(productId => 
            fetch(`${API_URL}/${productId}/remove-stock?quantity=${quantity}`, {
                method: 'PATCH'
            })
        );

        const results = await Promise.all(promises);
        const successful = results.filter(r => r.ok).length;
        
        showMessage(`Списано с ${successful} из ${selected.length} товаров`, 'success');
        closeBulkRemoveModal();
        setTimeout(() => location.reload(), 1000);
    } catch (error) {
        showMessage('Ошибка массового списания: ' + error.message, 'error');
    }
}

// Генерировать отчет по пополнению
async function generateRestockReport() {
    try {
        const response = await fetch(`${API_URL}/low-stock`);
        const products = await response.json();
        
        if (products.length === 0) {
            showMessage('Все товары имеют достаточный запас', 'success');
            return;
        }

        // Создаем CSV отчет
        let csv = ['Товар,Текущий запас,Минимальный запас,Рекомендуемое пополнение,Поставщик'];
        
        products.forEach(product => {
            const recommended = Math.max(product.minStock * 2 - product.quantity, product.minStock);
            csv.push([
                product.name,
                product.quantity,
                product.minStock || 5,
                recommended,
                product.supplier || 'Не указан'
            ].join(','));
        });
        
        const csvContent = csv.join('\n');
        const blob = new Blob(['\ufeff' + csvContent], { type: 'text/csv;charset=utf-8;' });
        const link = document.createElement('a');
        link.href = URL.createObjectURL(blob);
        link.download = 'restock_report_' + new Date().toISOString().split('T')[0] + '.csv';
        link.click();
        
        showMessage('Отчет по пополнению сгенерирован', 'success');
    } catch (error) {
        showMessage('Ошибка генерации отчета: ' + error.message, 'error');
    }
}

// Отметить товары как "Нет в наличии"
async function markOutOfStock() {
    const selected = getSelectedProducts();
    if (selected.length === 0) {
        showMessage('Выберите товары для изменения статуса', 'warning');
        return;
    }

    if (!confirm(`Отметить ${selected.length} товаров как "Нет в наличии"?`)) {
        return;
    }

    try {
        const promises = selected.map(async productId => {
            // Получаем товар
            const getResponse = await fetch(`${API_URL}/${productId}`);
            const product = await getResponse.json();
            
            // Обновляем статус и количество
            product.status = 'OUT_OF_STOCK';
            product.quantity = 0;
            
            return fetch(`${API_URL}/${productId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(product)
            });
        });

        const results = await Promise.all(promises);
        const successful = results.filter(r => r.ok).length;
        
        showMessage(`Обновлено ${successful} из ${selected.length} товаров`, 'success');
        setTimeout(() => location.reload(), 1000);
    } catch (error) {
        showMessage('Ошибка обновления статуса: ' + error.message, 'error');
    }
}

// Обновление таблицы товаров
function updateInventoryTable(products) {
    const tbody = document.getElementById('inventoryBody');
    tbody.innerHTML = '';

    if (products.length === 0) {
        tbody.innerHTML = '<tr><td colspan="7" style="text-align: center;">Товары не найдены</td></tr>';
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
        
        row.innerHTML = `
            <td>
                <input type="checkbox" class="product-checkbox" value="${product.id}">
            </td>
            <td>
                <strong>${product.name}</strong><br>
                <small>${product.supplier || 'Поставщик не указан'}</small>
            </td>
            <td>${product.category || 'Не указана'}</td>
            <td>
                <span style="font-size: 1.2rem; font-weight: bold;">${product.quantity}</span>
                <div style="font-size: 0.8rem; color: #666;">
                    Стоимость: ${(product.quantity * product.price).toFixed(0)} ₽
                </div>
            </td>
            <td>${product.minStock || 5}</td>
            <td>
                <input type="number" class="stock-input" min="1" value="1" id="add-${product.id}">
                <button class="btn-small btn-success" onclick="quickAddStock(${product.id})">➕</button>
                <button class="btn-small btn-warning" onclick="quickRemoveStock(${product.id})">➖</button>
            </td>
            <td>
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
        case 'warning':
            messageDiv.style.backgroundColor = '#f39c12';
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

// Закрытие модальных окон по клику вне их
window.onclick = function(event) {
    const bulkAddModal = document.getElementById('bulkAddModal');
    const bulkRemoveModal = document.getElementById('bulkRemoveModal');
    
    if (event.target === bulkAddModal) {
        closeBulkAddModal();
    }
    if (event.target === bulkRemoveModal) {
        closeBulkRemoveModal();
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