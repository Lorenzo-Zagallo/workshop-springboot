async function getProducts() {
    const resposta = await fetch('http://localhost:8080/workshop/products');
    const produtos = await resposta.json();

    const list = document.getElementById('products-list');

    produtos.forEach(produto => {

        const item = document.createElement('li'); // cria um item da lista(li)

        // modificando o CSS de 'item'
        item.style.listStyle = "none";
        item.style.display = "flex";
        item.style.alignItems = "center";
        item.style.marginBottom = "15px";


        const imgProduct = document.createElement('img'); // cria uma imagem(img)

        // modificando o CSS de 'imgProduct'
        imgProduct.src = produto.imgUrl;
        imgProduct.width = 250;
        imgProduct.style.margin = "10px";

        item.appendChild(imgProduct); // adiciona o elemento imagem na lista(li)


        const textProduct = document.createElement('p'); // cria um elemento parágrafo(p)
                
        // modificando o CSS
        textProduct.innerHTML = `Titulo: ${produto.name} <br> 
                                Preço: R$ ${produto.price} <br> 
                                Descrição: ${produto.description}`;
        textProduct.style.fontSize = "32px";
        textProduct.style.color = "#333";
        item.appendChild(textProduct); // adiciona o elemento parágrafo na lista(li)

        list.appendChild(item); // adiciona a lista(li) na list
    });
}

getProducts();