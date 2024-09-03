document.addEventListener ('DOMContentLoaded',async () => {
    const { default: filterAndSearch } = await import('./modulo.js');
    const filterSelect = document.getElementById('filterSelect');
    const searchInput = document.getElementById('searchInput');
    const favorites = JSON.parse(localStorage.getItem('favorites')) || [];


// Função de filtragem
filterSelect.addEventListener('change', () => {
    const collection = filterSelect.value;
    filterAndSearch(collection, searchInput.value.toLowerCase());
    console.log("filtragem efetuada atraves do filtro de seleção");
    
});

// Função de busca
searchInput.addEventListener('input', () => {
    const searchTerm = searchInput.value.toLowerCase();
    filterAndSearch(filterSelect.value, searchTerm);
});

// Seta o valor inicial da checkbox baseado no localstorage
const checkboxes = document.querySelectorAll('.favorite-checkbox');
checkboxes.forEach(checkbox => {
    const catalogItem = checkbox.closest('.catalog-item');
    const itemId = catalogItem.getAttribute('data-id');
    if (favorites.includes(itemId)) {
        checkbox.checked = true;
    }

    checkbox.addEventListener('change', function() {
        if (this.checked) {
            addToFavorites(itemId);
        } else {
            removeFromFavorites(itemId);
        }
    });
});


        //adiciona aos favoritos

        function addToFavorites(itemId) {
            if (!favorites.includes(itemId)) {
                favorites.push(itemId);
                localStorage.setItem('favorites', JSON.stringify(favorites));
                console.log(`Item ${itemId} adicionado aos favoritos`);
                console.log(`item clicado ${item.checkboxInput}`);
                
            }
        }
    
            //remove dos favoritos
    
        function removeFromFavorites(itemId) {
            const index = favorites.indexOf(itemId);
            if (index !== -1) {
                favorites.splice(index, 1);
                localStorage.setItem('favorites', JSON.stringify(favorites));
                console.log(`Item ${itemId} removido dos favoritos`);
            }
        }
    });

    //imprime catalogo do db na tela

    fetch('../mocks/catalog.json')
    .then(response => response.json())    
    .then(data =>{

        console.log("fetch carregado com sucesso");

        const catalog = document.getElementById('catalog');

        data.forEach(item =>{

            const itemDiv = document.createElement('div');
            itemDiv.className = 'item';

            itemDiv.innerHTML = 
            `<div class="catalog-item" data-collection="colecao1" data-id="1" data-name="${item.name}">
        <img src="${item.image}" alt="${item.name}">
        <h3 class="nomeCores">${item.name}</h3><h4 class="nomeColec">${item.colection}</h4>
        <input type="checkbox" id="checkboxInput-${item.checkboxInput}" class="favorite-checkbox"> 
        <!-- Ao adicionar item, se atente ao checkboxInput, pois para o JS ler tem que haver o valor correto do item -->
        <label for="checkboxInput-${item.checkboxInput}" class="bookmark">
            <svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 384 512" class="svgIcon">
                <path d="M0 48V487.7C0 501.1 10.9 512 24.3 512c5 0 9.9-1.5 14-4.4L192 400 345.7 507.6c4.1 2.9 9 4.4 14 4.4c13.4 0 24.3-10.9 24.3-24.3V48c0-26.5-21.5-48-48-48H48C21.5 0 0 21.5 0 48z"></path>
            </svg>
        </label>
    </div>`;

    catalog.appendChild(itemDiv);
        });
    })

    .catch(error => console.error('Erro ao carregar o fetch', error));

    
