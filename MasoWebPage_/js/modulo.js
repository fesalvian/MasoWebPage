export function filterAndSearch(collection, searchTerm) {
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