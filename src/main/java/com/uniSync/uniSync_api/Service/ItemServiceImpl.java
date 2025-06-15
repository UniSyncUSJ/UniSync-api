package com.uniSync.uniSync_api.Service;

import com.uniSync.uniSync_api.Model.Item;
import com.uniSync.uniSync_api.Repository.ItemRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item addItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Item updateItem(Long itemId, Item updatedItem) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        item.setName(updatedItem.getName());
        item.setDescription(updatedItem.getDescription());
        item.setPrice(updatedItem.getPrice());
        item.setStock(updatedItem.getStock());

        return itemRepository.save(item);
    }

    @Override
    public Item updateStock(Long itemId, int addedStock) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        item.setStock(item.getStock() + addedStock);
        return itemRepository.save(item);
    }

    @Override
    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
    }

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public List<Item> getItemsByFaculty(Long facultyId) {
        return itemRepository.findByFaculty_Id(facultyId);
    }

    @Override
    public List<Item> getItemsByDepartment(Long departmentId) {
        return itemRepository.findByDepartment_Id(departmentId);
    }

    @Override
    public List<Item> getItemsBySociety(Long societyId) {
        return itemRepository.findBySociety_Id(societyId);
    }
}

