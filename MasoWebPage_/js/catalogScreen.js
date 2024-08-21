
import filterAndSearch from "./modelo.js";

document.addEventListener ('DOMContentLoaded', () => {
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

