import java.util.*;
import backend.*;
import utils.*;
public class ShoppingCartState extends WareState {
  private static ShoppingCartState shoppingCartState;
  private static Warehouse warehouse;
  private static final int EXIT = 0;
  private static final int VIEW_CART = 1;
  private static final int ADD_TO_CART = 2;
  private static final int MODIFY_CART = 3;
  private static final int HELP = 4;
  private ShoppingCartState() {
    warehouse = Warehouse.instance();
  }

  public static ShoppingCartState instance() {
    if (shoppingCartState == null) {
      return shoppingCartState = new ShoppingCartState();
    } else {
      return shoppingCartState;
    }
  }

  public int getCommand() {
    do {
      try {
        int value = Integer.parseInt(InputUtils.getToken("Enter command:" + HELP + " for help"));
        if (value >= EXIT && value <= HELP) {
          return value;
        }
      } catch (NumberFormatException nfe) {
        System.out.println("Enter a number");
      }
    } while (true);
  }

  public void help() {
    System.out.println("\nEnter a number between " + EXIT + " and " + HELP + " as explained below:");
    System.out.println(VIEW_CART + " to view your shopping cart");
    System.out.println(ADD_TO_CART + " to add products to your shopping cart");
    System.out.println(MODIFY_CART + " to modify or remove items from your shopping cart");
    System.out.println("\n" + HELP + " for help");
    System.out.println(EXIT + " to return to client menu");
  }

  public void viewCart() {
    String clientId = WareContext.instance().getUser();
    Client client = warehouse.getClientById(clientId);
    System.out.println("\n  Shopping Cart Contents:\n");
    Iterator<ShoppingCartItem> cIterator = client.getShoppingCart().getShoppingCartProducts();
    while(cIterator.hasNext()) {
      ShoppingCartItem item = cIterator.next();
      System.out.println("Product id: " + item.getProduct().getId() + ", name: " + item.getProduct().getName() + 
        ", sale price: $" + item.getProduct().getSalePrice() + ", Quantity in cart: " + item.getQuantity());
    }
    System.out.println("\n  End of cart. \n" );
  }
  
  public void addToCart() {
    String clientId = WareContext.instance().getUser();
    do {
      String productId = InputUtils.getToken("Enter product id");
      Product product = warehouse.getProductById(productId);
      if(product != null) {
        System.out.println("Product found:");
        System.out.println("id:" + product.getId() + ", name: " + product.getName() + ", Sale Price: $" + product.getSalePrice() + "\n");
        int productQuantity = InputUtils.getNumber("Enter quantity");
        warehouse.addToCart(clientId, product, productQuantity);
      } else {
        System.out.println("Could not find that product id");
      }
      if (!InputUtils.yesOrNo("Add another product to the shopping cart?")) {
        break;
      }
    } while (true);
  }

  public void modifyCart() {
    String clientId = WareContext.instance().getUser();
    Client client = warehouse.getClientById(clientId);
    ShoppingCart cart = client.getShoppingCart();
    Boolean doneEditing = false;

    while (!doneEditing) {
      viewCart();
      String productId = InputUtils.getToken("Enter Product ID from cart to edit");

      // find the product in the shopping cart
      ShoppingCartItem item = null;
      Iterator<ShoppingCartItem> cartIter = cart.getShoppingCartProducts();
      while ( cartIter.hasNext() ) {
        ShoppingCartItem next = cartIter.next();
        if (next.getProduct().getId().equals(productId)) {
          item = next;
          break;
        }
      }

      if ( item == null ) {
        doneEditing = !InputUtils.yesOrNo("That ID was not found in the shopping cart? Continue?");
      } else {
        int newQuantity = InputUtils.getNumber("Enter the desired quantity, or '0' to remove the product from you cart.");
        if(newQuantity == 0) {
          if(cart.removeProductFromCart(warehouse.getProductById(productId))) {
            System.out.println("The product has been removed from your cart.");
          } else {
            System.out.println("Error: Product was unable to be removed from your cart.");
          }
        } else if (newQuantity > 0) {
          item.setQuantity(newQuantity);
        } else {
          System.out.println("Invalid input. Please enter a number greater than or equal to 0.");
        }

        
        doneEditing = !InputUtils.yesOrNo("Would you like to edit more items in your cart?");
      }
    }
  }

  public void process() {
    int command;
    help();
    while ((command = getCommand()) != EXIT) {
      switch (command) {
        case HELP:
          help();
          break;
        case VIEW_CART:
          viewCart();
          break;
        case ADD_TO_CART:
          addToCart();
          break;
        case MODIFY_CART:
          modifyCart();
          break;
        
        default:
          System.out.println("Invalid choice");
      }
    }
    logout();
  }

  public void run() {
    process();
  }

  public void logout() {
    (WareContext.instance()).changeState(0); // exit to ClientState with a code 0
  }
  
}
