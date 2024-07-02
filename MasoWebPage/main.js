document.addEventListener('DOMContentLoaded', () => {
    const catalogItems = document.querySelectorAll('.catalog-item');
    const checkboxes = document.querySelectorAll('.favorite-checkbox');
    const cards = document.querySelectorAll('.card');
    const filterSelect = document.getElementById('filterSelect');
    const searchInput = document.getElementById('searchInput');
    const favorites = JSON.parse(localStorage.getItem('favorites')) || [];
    const navButtons = document.querySelectorAll('.navButtons .btn');
    const currentPage = window.location.pathname.split('/').pop();
    

    //resposta visual dos botoes de navegação (ficam em branco na pagina que vc esta)
    navButtons.forEach(button => {
        const buttonPage = button.closest('a').getAttribute('href'); // Obtém o href do link pai
        if (buttonPage === currentPage) {
        button.classList.add('active');
        }
    });

        //funcao de redirecionamento dos cards da home page
        cards.forEach(card => {
            card.addEventListener('click', () => {
                console.log("card de filtro clicado");
                const filter = card.getAttribute('data-filter');
                console.log(`Redirecting to catalogo.html with filter: ${filter}`);
                window.location.href = `catalogo.html?filter=${filter}`;
            });
        });

 // Função de filtragem
filterSelect.addEventListener('change', () => {
    const collection = filterSelect.value;
    filterAndSearch(collection, searchInput.value.toLowerCase());
});

// Função de busca
searchInput.addEventListener('input', () => {
    const searchTerm = searchInput.value.toLowerCase();
    filterAndSearch(filterSelect.value, searchTerm);
});

function filterAndSearch(collection, searchTerm) {
    catalogItems.forEach(item => {
        const itemCollection = item.dataset.collection;
        const itemName = item.dataset.name.toLowerCase();
        
        if ((collection === 'all' || itemCollection === collection) && itemName.includes(searchTerm)) {
            item.style.display = 'block';
        } else {
            item.style.display = 'none';
        }
    });
}



// Verifica se há um parâmetro de filtro na URL e aplica o filtro correspondente
const urlParams = new URLSearchParams(window.location.search);
const filter = urlParams.get('filter');
if (filter) {
    filterSelect.value = filter;
    filterAndSearch(filter, searchInput.value.toLowerCase());
};

    // Carregar favoritos do localStorage
    function loadFavorites() {
        catalogItems.forEach(item => {
            const itemId = item.dataset.id;
            if (favorites.includes(itemId)) {
                item.classList.add('favorite');
                const checkbox = item.querySelector('.favorite-checkbox');
                if (checkbox) {
                    checkbox.checked = true;
                }
            }
        });
    }

    loadFavorites();

    // Seta o valor inicial da checkbox baseado no localstorage
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

        //pagina de favoritos funcionalidades

    const favoritesCatalog = document.getElementById('favoritesCatalog');
    if (favoritesCatalog) {
        loadFavoriteItems();
    }

    function loadFavoriteItems() {
        const favorites = JSON.parse(localStorage.getItem('favorites')) || [];
        console.log(favorites);
        favoritesCatalog.innerHTML = '';

        catalogItems.forEach(item => {
            const itemId = item.dataset.id;
            if (favorites.includes(itemId)) {
                const itemClone = item.cloneNode(true);
                const checkbox = itemClone.querySelector('.favorite-checkbox');
                if (checkbox) {
                    checkbox.checked = true;
                    checkbox.addEventListener('change', function() {
                        if (!this.checked) {
                            removeFavorite(itemClone, itemId);
                        }
                    });
                }
                favoritesCatalog.appendChild(itemClone);
            }
        });
    }

    function removeFavorite(item, itemId) {
        item.remove();
        let favorites = JSON.parse(localStorage.getItem('favorites')) || [];
        favorites = favorites.filter(fav => fav !== itemId);
        localStorage.setItem('favorites', JSON.stringify(favorites));
        const originalItem = document.querySelector(`.catalog-item[data-id="${itemId}"]`);
        if (originalItem) {
            originalItem.classList.remove('favorite');
            const originalCheckbox = originalItem.querySelector('.favorite-checkbox');
            if (originalCheckbox) {
                originalCheckbox.checked = false;
            }
        }
    }
});
