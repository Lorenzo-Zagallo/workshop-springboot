const routes = {
    users: "http://localhost:8080/workshop/users",
    products: "http://localhost:8080/workshop/products",
    orders: "http://localhost:8080/workshop/orders",
    categories: "http://localhost:8080/workshop/categories"
}

const apiService = {

    async getUsers() {
        const route = routes.users;
        const response = await fetch(route);
        return await response.json();
    },

    async getProducts() {
        const route = routes.products;
        const response = await fetch(route);
        return await response.json();
    },

    async getOrders() {
        const route = routes.orders;
        const response = await fetch(route)
        return await response.json();
    },

    async getCategories() {
        const route = routes.categories;
        const response = await fetch(route)
        return await response.json();
    }
}

const state = {
    views: {
        list_users: document.querySelector("#users-list"),
        list_products: document.querySelector("#products-list"),
        list_orders: document.querySelector("#orders-list"),
        list_categories: document.querySelector("#categories-list")
    }
}

async function main() {
    await loadUsers();
    await loadProducts();
    await loadOrders();
    await loadCategories();
}



async function loadUsers() {
    const users = await apiService.getUsers();
    createUsers(users)
}

async function createUsers(users) {
    users.forEach(user => {
        const item = document.createElement('li');

        const textUser = document.createElement('p');

        // modificando o CSS
        textUser.innerHTML = `Nome: ${user.name} <br>
                            Email: ${user.email} <br>
                            Phone: ${user.phone} <br>
                            Password: ${user.password}`;
        
        item.appendChild(textUser); // adiciona o elemento parágrafo na lista(li)

        state.views.list_users.appendChild(item); // adiciona a lista(li) na list
    });
}



async function loadProducts() {
    const products = await apiService.getProducts();
    createProducts(products)
}

async function createProducts(products) {
    products.forEach(product => {
        const item = document.createElement('li'); // cria um item da lista(li)
        
        const imgProduct = document.createElement('img'); // cria uma imagem(img)
        imgProduct.src = product.imgUrl;
        item.appendChild(imgProduct); // adiciona o elemento imagem na lista(li)

        const textProduct = document.createElement('p'); // cria um elemento parágrafo(p)
        textProduct.innerHTML = `Título: ${product.name} <br>
                                Preço: R$ ${product.price} <br>
                                Descrição: ${product.description}`;
        item.appendChild(textProduct) // adiciona o elemento parágrafo na lista(li)

        state.views.list_products.appendChild(item)
    })
}



async function loadOrders() {
    const orders = await apiService.getOrders();
    createOrders(orders)
}

async function createOrders(orders) {
    orders.forEach(order => {
        const item = document.createElement('li');

        const textOrder = document.createElement('p');
        textOrder.innerHTML = `Momento: ${order.moment} <br>
                            Estado do pedido: ${order.orderStatus} <br>
                            Cliente: <br>
                            - Id: ${order.client.id} <br>
                            - Nome: ${order.client.name} <br>
                            - Email: ${order.client.email} <br> 
                            - Telefone: ${order.client.phone} <br> 
                            - Password: ${order.client.password} <br>
                            Items: ${order.items} <br>
                            Pagamento: ${order.payment} <br>
                            Total: ${order.total}`
        item.appendChild(textOrder);

        state.views.list_orders.appendChild(item);
    })
}



async function loadCategories() {
    const categories = await apiService.getCategories();
    createCategories(categories);
}

async function createCategories(categories) {
    categories.forEach(category => {
        const item = document.createElement('li');

        const textCategory = document.createElement('p');
        textCategory.innerHTML = `Id: ${category.id} <br>
                                Nome: ${category.name}`;
        item.appendChild(textCategory);

        state.views.list_categories.appendChild(item);
    })
}

main();