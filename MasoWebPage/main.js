document.addEventListener('DOMContentLoaded', () => {
    const filterButtons = document.querySelectorAll('.filter-btn');
    const catalogItems = document.querySelectorAll('.catalog-item');
    const checkboxes = document.querySelectorAll('.favorite-checkbox');

    const favorites = JSON.parse(localStorage.getItem('favorites')) || [];
    console.log(favorites);

    // Função de filtragem
    filterButtons.forEach(button => {
        button.addEventListener('click', () => {
            const collection = button.dataset.collection;

            catalogItems.forEach(item => {
                if (collection === 'all' || item.dataset.collection === collection) {
                    item.style.display = 'block';
                } else {
                    item.style.display = 'none';
                }
            });
        });
    });

    // Carregar favoritos do localStorage
    function loadFavorites() {
        catalogItems.forEach(item => {
            const itemId = item.dataset.id;
            if (favorites.includes(itemId)) {
                item.classList.add('favorite');
            }
        });
    }

    loadFavorites();

    // Set the initial state of the checkboxes based on LocalStorage
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

    function addToFavorites(itemId) {
        if (!favorites.includes(itemId)) {
            favorites.push(itemId);
            localStorage.setItem('favorites', JSON.stringify(favorites));
            console.log(`Item ${itemId} adicionado aos favoritos`);
        }
    }

    function removeFromFavorites(itemId) {
        const index = favorites.indexOf(itemId);
        if (index !== -1) {
            favorites.splice(index, 1);
            localStorage.setItem('favorites', JSON.stringify(favorites));
            console.log(`Item ${itemId} removido dos favoritos`);
        }
    }

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
