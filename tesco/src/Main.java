// TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Tesco gets millions of orders every day with an average basket size of 100 items. Tesco Business
 * has got some regulations around selling products online and in stores. These regulations are
 * mandatory from legal and business perspective to enforce for all order transactions. You are
 * given an order with a list of products in the shopping cart/basket with productid, product
 * Category and quantity. And also, Restriction Rules on Qty and Qty/Category. Example: Ordered
 * items in the shopping cart/basket Item-1 -> productid=1, category=Paracetamol, quantity=3 Item-2
 * -> productid=2, category=analgesic, quantity=3 Item-3 -> productid=3, category=chocolate,
 * quantity=8 Item-4 -> productid=4, category= Paracetamol, quantity=2
 *
 * <p>Business Restriction rules: Cannot buy more than 10 Quantity of any products - BulkBuyLimit
 * Cannot buy more than 5 Quantity of paracetamol products – BulkBuyLimitCategory Cannot buy more
 * than 3 Quantity of chocolate products – BulkBuyLimitCategory
 *
 * <p>Order(2 P, 4 C) : false
 *
 * <p>Write a restriction rule engine to run the restriction check against the shopping cart/basket
 * and return the status as to MET/BREACHED indicating restriction status for the given restriction
 * rules. For the above given example, the restriction status returned would be MET
 */
public class Main {

  static enum Category {
    Paracetamol,
    analgesic,
    chocolate
  }

  static enum RuleType {
    global,
    category,
    price
  }

  static class Rule {
    RuleType type;
    int quantity;
    Category category;

    @Override
    public String toString() {
      return "Rule{" + "type=" + type + ", quantity=" + quantity + ", category=" + category + '}';
    }

    public Rule(RuleType type, int quantity) {
      this.type = type;
      this.quantity = quantity;
    }

    public Rule(RuleType type, int quantity, Category category) {
      this.type = type;
      this.quantity = quantity;
      this.category = category;
    }

    public RuleType getType() {
      return type;
    }

    public Rule setType(RuleType type) {
      this.type = type;
      return this;
    }

    public int getQuantity() {
      return quantity;
    }

    public Rule setQuantity(int quantity) {
      this.quantity = quantity;
      return this;
    }

    public Category getCategory() {
      return category;
    }

    public Rule setCategory(Category category) {
      this.category = category;
      return this;
    }
  }

  static class RuleSet {
    List<Rule> globalRules;
    List<Rule> categoryRules;

    @Override
    public String toString() {
      return "RuleSet{" + "globalRules=" + globalRules + ", categoryRules=" + categoryRules + '}';
    }

    public RuleSet() {
      globalRules = new ArrayList<>();
      categoryRules = new ArrayList<>();
    }

    public List<Rule> getGlobalRules() {
      return globalRules;
    }

    public RuleSet setGlobalRules(List<Rule> globalRules) {
      this.globalRules = globalRules;
      return this;
    }

    public List<Rule> getCategoryRules() {
      return categoryRules;
    }

    public RuleSet setCategoryRules(List<Rule> categoryRules) {
      this.categoryRules = categoryRules;
      return this;
    }
  }

  static class Product {
    int id;
    Category category;
    int quantity;

    public Product(int id, Category category, int quantity) {
      this.id = id;
      this.category = category;
      this.quantity = quantity;
    }

    public int getId() {
      return id;
    }

    public Product setId(int id) {
      this.id = id;
      return this;
    }

    public Category getCategory() {
      return category;
    }

    public Product setCategory(Category category) {
      this.category = category;
      return this;
    }

    public int getQuantity() {
      return quantity;
    }

    public Product setQuantity(int quantity) {
      this.quantity = quantity;
      return this;
    }
  }

  static class Inventory {
    Map<Category, List<Product>> productMap;
    Map<Category, Integer> productCountMap;

    @Override
    public String toString() {
      return "Inventory{"
          + "productMap="
          + productMap
          + ", productCountMap="
          + productCountMap
          + '}';
    }

    public Inventory() {
      this.productMap = new ConcurrentHashMap<>();
      this.productCountMap = new ConcurrentHashMap<>();
    }

    public Map<Category, List<Product>> getProductList() {
      return productMap;
    }

    public Inventory setProductMap(Map<Category, List<Product>> productMap) {
      this.productMap = productMap;
      return this;
    }

    public void addIntInventory(Category category, Product product) {
      if (category == null || product == null) {
        throw new IllegalArgumentException("Category and Product cannot be null");
      }

      productMap.computeIfAbsent(category, k -> new ArrayList<>()).add(product);
      productCountMap.merge(category, product.getQuantity(), Integer::sum);
    }
  }

  static class OrderItem {
    int id;
    int productID;
    int quantity;
    Category category;

    public OrderItem(int id, int productID, int quantity, Category category) {
      this.id = id;
      this.productID = productID;
      this.quantity = quantity;
      this.category = category;
    }

    public int getId() {
      return id;
    }

    public OrderItem setId(int id) {
      this.id = id;
      return this;
    }

    public int getProductID() {
      return productID;
    }

    public OrderItem setProductID(int productID) {
      this.productID = productID;
      return this;
    }

    public int getQuantity() {
      return quantity;
    }

    public OrderItem setQuantity(int quantity) {
      this.quantity = quantity;
      return this;
    }

    public Category getCategory() {
      return category;
    }

    public OrderItem setCategory(Category category) {
      this.category = category;
      return this;
    }
  }

  static class Order {
    List<OrderItem> orderItems;
    Map<Category, Integer> productCountMap;

    public Order() {
      this.orderItems = new ArrayList<>();
      productCountMap = new ConcurrentHashMap<>();
    }

    public Order(List<OrderItem> orderItems) {
      this.orderItems = orderItems;
    }

    public List<OrderItem> getOrderItems() {
      return orderItems;
    }

    public void addOrderItem(OrderItem item) {
      if (item == null) {
        throw new IllegalArgumentException("OrderItem cannot be null");
      }

      this.orderItems.add(item);
      Category category = item.getCategory();
      if (category != null) {
        productCountMap.merge(category, item.getQuantity(), Integer::sum);
      }
    }

    public Order setOrderItems(List<OrderItem> orderItems) {
      this.orderItems = orderItems;
      return this;
    }
  }

  public static void main(String[] args) {
    Inventory inventory = new Inventory();
    Product p1 = new Product(1, Category.Paracetamol, 3);
    inventory.addIntInventory(Category.Paracetamol, p1);
    Product p4 = new Product(4, Category.Paracetamol, 2);
    inventory.addIntInventory(Category.Paracetamol, p1);
    Product p2 = new Product(2, Category.analgesic, 3);
    inventory.addIntInventory(Category.analgesic, p1);
    Product p3 = new Product(3, Category.chocolate, 8);
    inventory.addIntInventory(Category.chocolate, p3);

    System.out.println(inventory);

    Rule rule1 = new Rule(RuleType.global, 10);

    Rule rule2 = new Rule(RuleType.category, 5, Category.Paracetamol);
    Rule rule3 = new Rule(RuleType.category, 3, Category.chocolate);

    // checks; global , category 2 : global , category
    // checks : global , category, prices,  2 : global ,category, prices
    RuleSet ruleSet = new RuleSet();
    ruleSet.getGlobalRules().add(rule1);

    ruleSet.getCategoryRules().add(rule2);
    ruleSet.getCategoryRules().add(rule3);

    OrderItem orderItem1 = new OrderItem(1, 1, 1, Category.Paracetamol);
    OrderItem orderItem2 = new OrderItem(1, 4, 1, Category.Paracetamol);
    OrderItem orderItem3 = new OrderItem(1, 2, 6, Category.chocolate);

    Order order = new Order();
    order.addOrderItem(orderItem1);
    order.addOrderItem(orderItem2);
    order.addOrderItem(orderItem3);
    System.out.println(verifyOrderItems(inventory, ruleSet, order));
  }

  // factory for rule set, if global global rule check
  //
  static boolean verifyOrderItems(Inventory inventory, RuleSet ruleSet, Order order) {
    List<OrderItem> items = order.getOrderItems();
    List<Rule> globalRules = ruleSet.getGlobalRules();
    int orderQuantity = 0;

    Map<Category, Integer> productCountMap = order.productCountMap;
    for (Map.Entry<Category, Integer> entry : productCountMap.entrySet()) {
      Category key = entry.getKey();
      Integer value = entry.getValue();
      if (inventory.productCountMap.get(key) < value) {
        System.out.println("failed in Category Count ");

        return false;
      }
    }

    for (Rule rule : globalRules) {
      int ruleQuantity = rule.getQuantity();
      for (OrderItem item : items) {
        orderQuantity += item.getQuantity();
      }
      if (orderQuantity > ruleQuantity) {
        return false;
      }
    }

    List<Rule> categoryRules = ruleSet.getCategoryRules();

    for (Rule rule : categoryRules) {
      Category category = rule.getCategory();
      int ruleQuantity = rule.getQuantity();
      AtomicInteger categoryQuantity = new AtomicInteger();
      items.stream()
          .filter(item -> item.getCategory().equals(category))
          .forEach(
              item -> {
                categoryQuantity.addAndGet(item.getQuantity());
              });
      System.out.println("Category Quaktiy : " + categoryQuantity);
      if (categoryQuantity.get() > ruleQuantity) {
        return false;
      }
    }
    return true;
  }
}
