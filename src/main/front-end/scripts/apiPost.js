const routes = {
    users: "http://localhost:8080/workshop/users",
    products: "http://localhost:8080/workshop/products",
    orders: "http://localhost:8080/workshop/orders",
    categories: "http://localhost:8080/workshop/categories"
}

const apiService = {

    async postUsers() {
        const route = routes.users;
        const response = await fetch(route);
        return await response.json();
    },

    async postProducts() {
        const route = routes.products;
        const response = await fetch(route);
        return await response.json();
    },

    async postOrders() {
        const route = routes.orders;
        const response = await fetch(route);
        return await response.json();
    },

    async postCategories() {
        const route = routes.categories;
        const response = await fetch(route);
        return await response.json();
    }
}