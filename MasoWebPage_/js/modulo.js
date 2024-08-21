export default function filterAndSearch(collection, searchTerm) {
    const catalogItems = document.querySelectorAll('.catalog-item');
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