document.addEventListener('DOMContentLoaded', () => {
    const filterButtons = document.querySelectorAll('.filter-btn');
    const catalogItems = document.querySelectorAll('.catalog-item');
    const favoriteButtons = document.querySelectorAll('.favorite-btn');
    const removeFavoriteButtons = document.querySelectorAll('.remove-favorite-btn');

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

    // Função de favoritar
    favoriteButtons.forEach(button => {
        button.addEventListener('click', () => {
            const item = button.parentElement;
            const itemId = item.dataset.id;
            let favorites = getFavorites();

            if (!favorites.includes(itemId)) {
                favorites.push(itemId);
                localStorage.setItem('favorites', JSON.stringify(favorites));
                item.classList.add('favorite');
            }
        });
    });

    // Função de remover dos favoritos
    removeFavoriteButtons.forEach(button => {
        button.addEventListener('click', () => {
            const item = button.parentElement;
            const itemId = item.dataset.id;
            let favorites = getFavorites();

            favorites = favorites.filter(fav => fav !== itemId);
            localStorage.setItem('favorites', JSON.stringify(favorites));
            item.classList.remove('favorite');
        });
    });

    // Carregar favoritos do localStorage
    function loadFavorites() {
        const favorites = getFavorites();

        catalogItems.forEach(item => {
            const itemId = item.dataset.id;
            if (favorites.includes(itemId)) {
                item.classList.add('favorite');
            }
        });
    }

    // Obter favoritos do localStorage
    function getFavorites() {
        return JSON.parse(localStorage.getItem('favorites')) || [];
    }

    loadFavorites();

    // Funcionalidade para a página de favoritos
    const favoritesCatalog = document.getElementById('favoritesCatalog');
    if (favoritesCatalog) {
        loadFavoriteItems();
    }

    function loadFavoriteItems() {
        const favorites = getFavorites();
        favoritesCatalog.innerHTML = '';

        catalogItems.forEach(item => {
            const itemId = item.dataset.id;
            if (favorites.includes(itemId)) {
                const itemClone = item.cloneNode(true);
                itemClone.querySelector('.favorite-btn').textContent = 'Remover dos Favoritos';
                itemClone.querySelector('.favorite-btn').style.display = 'none'; // Ocultar botão de favoritar
                itemClone.querySelector('.remove-favorite-btn').addEventListener('click', () => {
                    removeFavorite(itemClone, itemId);
                });
                favoritesCatalog.appendChild(itemClone);
            }
        });
    }

    function removeFavorite(item, itemId) {
        item.remove();
        let favorites = getFavorites();
        favorites = favorites.filter(fav => fav !== itemId);
        localStorage.setItem('favorites', JSON.stringify(favorites));
        const originalItem = document.querySelector(`.catalog-item[data-id="${itemId}"]`);
        if (originalItem) {
            originalItem.classList.remove('favorite');
        }
    }
});
