import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Lambda on 11/16/2015.
 */

/**
 * ServiceCenter CAN TALK TO DATABASE FOR UPDATING ALL THE
 * SALE RECORDS
 */
public class ServiceCenter implements Observer {
  private final String _account     = "12345";
  private Socket       _socket      = null;
  private int          _port_num    = -1;
  private String       _domain_name = null;

  /**
   * CONSTRUCTOR
   *
   * TAKING INPUT FROM USER AND PRODUCE A SERVICE CENTER OBJECT
   *
   * @param domain   DOMAIN NAME (SERVER DOMAIN NAME)
   * @param port_num PORT NUMBER TO COMMUNICATE WITH SERVER
   * @throws IOException INDICATES IF THE FAILURE OF CONNECTION TO SERVER
   */
  public ServiceCenter(String domain, int port_num) throws IOException {
    this._port_num    = port_num;
    this._domain_name = domain;
    this._socket      = new Socket(domain, port_num); // FOR TESTING PURPOSE
    this._socket.close();
    this._socket      = null;
  }

  /**
   * update METHOD (PUBLIC)
   *
   * OBSERVER METHOD TO GET THE UPDATES FROM THE OBSERVABLES; AFTER RECEIVING
   * NOTIFICATION FROM THE OBSERVABLES, IT WOULD FIRST GENERATE THE REQUEST
   * STRING AND THEN SEND THE REQUEST TO THE SERVER. (ORDERING)
   *
   * @param o   OBSERVABLE OBJECT - VENDING MACHINE
   * @param arg OBSERVABLE ARGUMENT
   */
  @Override
  public void update(Observable o, Object arg) {
    if(o instanceof VendingMachine){
      // TEST ///////////////////////////////////////////////////////////////
      System.out.println("INVENTORY UPDATED....");
      ///////////////////////////////////////////////////////////////////////
      String request_message = this._make_request_str(
          ((VendingMachine) o).restock_check()
      );
      try {
        this._send_request_to_server(request_message);
      } catch (IOException e) {
        String err_fmt = "[ERROR] FAIL TO TALK TO (DOMAIN : %s, SOCKET : %d)\n";
        System.err.println(String.format(
            err_fmt, this._domain_name, this._port_num
        ));
        e.printStackTrace();
      }
    } // END IF
  }

  /**
   * _make_request_str METHOD (PRIVATE)
   *
   * TAKING AN ARRAYLIST OF ITEMS THAT NEEDED TO BE RESTOCKED AND MAKE THE
   * REQUEST STRING
   *
   * @param list ARRAYLIST THAT CONTAINS THE PAIR OF PRODUCT ID AND NUMBER OF
   *             RESTOCK ITEMS
   * @return REQUEST STRING (ACCOUNT NUMBER : PRODUCT ID @ NUMBER TO RESTOCK)
   */
  private String _make_request_str(ArrayList<Pair<String, Integer>> list){
    String message = this._account;
    for(Pair<String, Integer> p : list){
      message += ":";
      //message += p.get_left();
      //message += ":";
      //message += p.get_right().toString();
      message += p.toString();
      message += ":";
    } // END FOR
    return message;
  } // END _make_request_str METHOD

  /**
   * _send_request_to_server METHOD (PRIVATE)
   *
   * SENDING REQUEST STRING TO THE SERVER
   *
   * @param message INDICATES THE REQUESTING ORDER STRING
   * @throws IOException INDICATES THE FAILURE OF CONNECTION TO THE SERVER
   */
  private void _send_request_to_server(String message) throws IOException {
    this._socket = new Socket(this._domain_name, this._port_num);
    OutputStream output_hdlr = this._socket.getOutputStream();
    output_hdlr.write(message.getBytes());
    output_hdlr.flush();
    output_hdlr.close();
    this._socket.close();
  }

  /**
   * disconnect_socket METHOD (PUBLIC)
   *
   * DISCONNECT FROM THE SERVER
   *
   * @throws IOException FAILURE TO CLOSE THE CONNECTION
   */
  public void disconnect_socket() throws IOException {
    this._socket.close();
  }
}