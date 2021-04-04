import java.util.*;
import backend.*;
import utils.*;
public class QueryClientState extends WareState {
  private static QueryClientState queryClientState = new QueryClientState();
  private static Warehouse warehouse;

  enum Operations {
    Exit,
    ShowClients,
    ShowClientsWithOutstandingBalance,
    ShowClientsWithNoTransactions,
    Help
  }

  private QueryClientState() {
    warehouse = Warehouse.instance();
  }

  public static QueryClientState instance() {
      return queryClientState;
  }

  public Operations getCommand() {
    do {
      try {
        int value = Integer.parseInt(InputUtils.getToken("Enter command:" + Operations.Help.ordinal() + " for help"));
        for ( Operations op : Operations.values() ) {
          if ( value == op.ordinal() ) {
            return op;
          }
        }
      } catch (NumberFormatException nfe) {
        System.out.println("Enter a number");
      }
    } while (true);
  }


  public void help() {
    System.out.println("\nEnter a number between " + Operations.Exit.ordinal() + " and " + Operations.Help.ordinal() + " as explained below:");
    System.out.println(Operations.ShowClients.ordinal() + " to show a list of all clients");
    System.out.println(Operations.ShowClientsWithOutstandingBalance.ordinal() + " to show a list clients with an outstanding balance");
    System.out.println(Operations.ShowClientsWithNoTransactions.ordinal() + " to show a list clients with no transactions\n");
    System.out.println(Operations.Exit.ordinal() + " to return to the clerk menu");
  }

  public void showClients() {
      Iterator<Client> allClients = warehouse.getClients();
      System.out.println("\n  List of all Clients: \n");
      while (allClients.hasNext()){
        Client client = allClients.next();
        System.out.println(client.toString());
      }
      System.out.println("");
  }

  public void showOutstandingBalances() {
    Iterator<Client> allClients = warehouse.getClients();
    System.out.println("\n  List of Clients with an outstanding balance: \n");
    while (allClients.hasNext()){
      Client client = allClients.next();
      if(client.getBalance() < 0) {
        System.out.println(client.toString());
      }
    }
    System.out.println("\n  End of Outstanding Client Balance list. \n");
  }

  public void showNoTransactions() {
    Iterator<Client> allClients = warehouse.getClients();
    System.out.println("\n  List of Clients with no transactions: \n");
    while (allClients.hasNext()){
      Client client = allClients.next();
      Iterator<Transaction> trans = client.getTransactionList().getTransactions();
      if(!trans.hasNext()) {
        System.out.println(client.toString());
      }
    }
    System.out.println("\n  End of Clients with no transactions list. \n");
  }

  public void process() {
    Operations command;
    help();
    while ((command = getCommand()) != Operations.Exit) {
      switch (command) {
        case Help:
          help();
          break;
        case ShowClients:
          showClients();
          break;
        case ShowClientsWithOutstandingBalance:
          showOutstandingBalances();
          break;
        case ShowClientsWithNoTransactions:
          showNoTransactions();
          break;

        default:
          System.out.println("Invalid command");
      }
    }
    logout();
  }

  public void run() {
    process();
  }

  public void logout()
  {
    (WareContext.instance()).changeState(0); // exit to ClerkState with a code 0
  }
 
}
