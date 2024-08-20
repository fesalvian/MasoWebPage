const catalogItems = document.querySelectorAll('.catalog-item');
// Função de filtragem
const filterSelect = document.getElementById('filterSelect');
filterSelect.addEventListener('change', () => {
    const collection = filterSelect.value;
    filterAndSearch(collection, searchInput.value.toLowerCase());
});

// Função de busca
const searchInput = document.getElementById('searchInput');
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
        };