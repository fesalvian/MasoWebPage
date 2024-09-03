document.addEventListener('DOMContentLoaded', async() => {

    const { default: filterAndSearch } = await import('./modulo.js');

       //pagina de favoritos funcionalidades

    const favoritesCatalog = document.getElementById('favoritesCatalog');
    if (favoritesCatalog) {
        loadFavoriteItems();
    }

    function loadFavoriteItems() {
        const favorites = JSON.parse(localStorage.getItem('favorites')) || [];
        console.log('Favoritos:',favorites);
        favoritesCatalog.innerHTML = '';

        const catalogItems = document.querySelectorAll('.catalog-item');

        catalogItems.forEach(item => {
            const itemId = item.dataset.id;
            if (favorites.includes(itemId)) {
                const itemClone = item.cloneNode(true);

                const itemName = item.dataset.name;
                const itemCollection = item.dataset.collection;
                const h3 = itemClone.querySelector('h3');
                if (h3) {
                    h3.textContent = `${itemName} - ${itemCollection}`;
                }

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
