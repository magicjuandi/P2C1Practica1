import domain.enums.ClientType;
import domain.enums.OrderType;
import domain.enums.ProductType;
import domain.models.Client;
import domain.models.Order;
import domain.models.Product;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static domain.enums.ClientType.LEVEL2;
import static java.lang.Double.sum;
import static java.util.stream.Collectors.toList;


public class Main {
    public static void main(String[] args) {
        Product product1 = new Product(1, "Car", ProductType.TOYS, 120000.0);
        Product product2 = new Product(2, "Book2", ProductType.BOOKS, 100000.0);
        Product product3 = new Product(3, "Sheets", ProductType.BABIES, 100000.0);
        Product product4 = new Product(4, "Book1", ProductType.BOOKS, 150000.0);

        List<Product> products = new ArrayList<Product>();

        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);
        products.toString();

        System.out.println(listByPriceIf(products,ProductType.BOOKS));

        List<Product> productsA = new ArrayList<Product>();
        productsA.add(product1);
        productsA.add(product2);

        List<Product> productsB = new ArrayList<Product>();
        productsB.add(product1);
        productsB.add(product3);
        productsB.add(product4);

        List<List<Product>> productsTotal = Arrays.asList(productsA, productsB);

        List<Product> productsUnited = productsTotal.stream()
                .flatMap(list -> list.stream())
                .collect(toList());
        Client client1 = new Client(1L,"Juan", ClientType.LEVEL1);
        Client client2 = new Client(2L,"Fernanda", LEVEL2);
        Client client3 = new Client(3L,"Pedro", ClientType.LEVEL3);

        List<Client> clients =new ArrayList<Client>();

        clients.add(client1);
        clients.add(client2);

        Order order1 = new Order(1L,
                OrderType.DELIVERED,
                LocalDate.of(2023,02,20),
                LocalDate.of(2023,03,24),
                productsA,
                client1);
        Order order2 = new Order(2L,
                OrderType.PENDING,
                LocalDate.of(2023,01,10),
                LocalDate.of(2023,01,14),
                productsB,
                client2);
        Order order3 = new Order(3L,
                OrderType.PENDING,
                LocalDate.of(2023,01,10),
                LocalDate.of(2023,01,14),
                productsB,
                client2);
        List<Order> orders = new ArrayList<Order>();

        orders.add(order1);
        orders.add(order2);
        orders.add(order3);

        System.out.println(listByCategoryTotal(productsUnited,ProductType.BABIES));//1
        System.out.println(listToys(products, ProductType.TOYS));//2
        System.out.println(getCheapProduct(products,ProductType.BOOKS));//3
        System.out.println(getLastOrders(orders,LocalDate.now()));//4
        System.out.println(getSizeOrders(orders,
                LocalDate.of(2023,01,02),
                LocalDate.of(2023,02,23)));//5

        System.out.println(getOrderProductType(orders,2));//6
        System.out.println(totalPrice(orders,2L));//7~ (No me sirve el LocalDate para separarlos)
        System.out.println(totalPriceProm(orders,2L));//8~ (No me sirve el LocalDate para separarlos)
        System.out.println(sortByClient(orders));//9
        System.out.println(listExpensive(productsUnited));//10

    }
    public static List<Product> listByCategoryTotal(List<Product> productsUnited,ProductType type){ //lista total por cada tipo
        return productsUnited.stream()
                .filter(p -> p.getCategory().equals(type))
                .toList();
    }

    public static List<Product> listByPriceIf(List<Product> products,ProductType type){ //punto 1
            return products.stream()
                    .filter(p -> p.getPrice()>100)
                    .filter(p -> p.getCategory().equals(type))
                    .toList();
    }
    public static List<Order> getOrderProductType(List<Order> orders,int numOrder) { //punto 2
        return orders.stream()
                .filter(p -> p.getProducts().get(numOrder-1).getCategory().equals(ProductType.BABIES))
                .toList();
    }
    public static List<Product> listToys(List<Product> products, ProductType type){ //punto 3
        return products.stream()
                .filter(p -> p.getCategory().equals(type))
                .map(p -> {
                    p.setPrice(p.getPrice() - p.getPrice() * 0.10);
                    return p;})
                .toList();
    }
    public static List<Order> getSizeOrders(List<Order> orders, LocalDate startDate,LocalDate endDate) { //punto 4
        return orders.stream()
                .filter(p -> p.getClient().getLevel().equals(LEVEL2))
                .filter(p -> p.getOrderDate().isBefore(endDate))
                .filter(p -> p.getOrderDate().isAfter(startDate))
                .toList();
    }
    public static List<Product> getCheapProduct(List<Product> products, ProductType type) { //punto 5
        return products.stream()
                .filter(p -> p.getCategory().equals(type))
                .sorted(Comparator.comparing(Product::getPrice))
                .limit(3)
                .toList();
    }
    public static List<Order> getLastOrders(List<Order> orders, LocalDate endDate) { //punto 6
        return orders.stream()
                .filter(p -> p.getOrderDate().isBefore(endDate))
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .limit(3)
                .toList();
    }
    public static double totalPrice(List<Order> orders,Long idNumber) {//punto 7
        return orders.stream()
                .filter(p -> p.getId()==idNumber)
                .flatMap(p -> p.getProducts().stream())
                .mapToDouble(p -> p.getPrice())
                .sum();
    }
    public static double totalPriceProm(List<Order> orders,Long idNumber) {//punto 8
        return orders.stream()
                .filter(p -> p.getId()==idNumber)
                .flatMap(p -> p.getProducts().stream())
                .mapToDouble(p -> p.getPrice())
                .average()
                .orElse(0.0);
    }
    public static Map<Client, List<Order>> sortByClient(List<Order> orders) { //punto 9
        return orders.stream()
                .collect(Collectors.groupingBy(Order::getClient,
                        HashMap::new,
                        Collectors.toList()));
    }
    public static Product listExpensive(List<Product> productsUnited){ //punto 10
        return productsUnited.stream()
                .max(Comparator.comparing(Product::getPrice))
                .orElseThrow(NoSuchElementException::new);
    }
}