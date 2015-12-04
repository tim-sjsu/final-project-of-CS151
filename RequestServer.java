import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.SocketHandler;

/**
 * Created by Lambda on 11/16/2015.
 */
public class RequestServer {
  private String _domain = null;
  private int _port_num = -1;
  private ServerSocket _server_socket = null;
  private ExecutorService _thread_pool = null;
  private List<Future<String>> _list = null;

  private final int _NUM_THREADS = 32;

  /**
   * CONSTRUCTOR
   *
   * GENERATE A REQUEST SERVER OBJECT AND ALSO INITIATE THE SERVER OPERATIONS
   *
   * @param domain   DOMAIN NAME OF THE SERVER
   * @param port_num PORT THAT SERVER LISTENS TO
   */
  public RequestServer(String domain, int port_num){
    this._domain = domain;
    this._port_num = port_num;

    this._init();
    this._run_server();
  }

  /**
   * _init METHOD (PRIVATE)
   *
   * CREATING THE SERVER BASED ON THE DOMAIN NAME AND PORT NUMBER PROVIDED BY
   * THE USER.
   */
  private void _init(){
    this._thread_pool = Executors.newFixedThreadPool(this._NUM_THREADS);
    try {
      this._server_socket = new ServerSocket(
          this._port_num, 0, InetAddress.getByName(null)
      );
    }
    catch (IOException e) {
      e.printStackTrace();
    } // END TRY-CATCH
    this._list = new ArrayList<Future<String>>();
  } // END init METHOD

  /**
   * _run_server METHOD (PRIVATE)
   *
   * START THE SERVER LOOP; IF THERE IS A CLIENT CONNECTING TO THE SERVER, IT
   * WOULD GENERATE A CHILD THREAD TO HANDLE THE WORK.
   */
  private void _run_server(){
    System.out.println("WAITING FOR CONNECTION....");
    while(true){
      Socket client = null;
      try{
        client = this._server_socket.accept();
        System.out.println("RELIEVED A CLIENT");
      }
      catch(IOException e){
        e.printStackTrace();
      }

      // CREATE WORKER
      this._list.add(this._thread_pool.submit(
          new Worker(client)
      ));

      if(this._list.size() == 5){
        synchronized (this._list){
          for(Future<String> fu: this._list){
            try {
              System.out.println("TEMP --->" + fu.get());
            } catch (InterruptedException e) {
              e.printStackTrace();
            } catch (ExecutionException e) {
              e.printStackTrace();
            }
          }
          this._list.clear();
        } // END synchronized BLOCK
      }

    } // END WHILE
  } // END _run_server METHOD


  /**
   * Worker CLASS
   *
   * THE WORKER CLASS IMPLEMENTS THE CALLABLE CLASS; IT WOULD PROCESS THE REQUEST
   * STRING AND RETURN THE REQUEST "ORDER" BACK TO THE MAIN THREAD.
   */
  public class Worker implements Callable {
    private Socket _client_socket = null;
    private String _result = null;


    /**
     * CONSTRUCTOR
     *
     * GENERATE A WORKER OBJECT AND ALSO SET UP THE CONNECTION INFORMATION (
     * SOCKET INFORMATION )
     *
     * @param client SOCKET OBJECT THAT REPRESENTS THE CLIENT CONNECTION
     */
    public Worker(Socket client){
      this._client_socket = client;
    }

    /**
     * call METHOD (PUBLIC OVERRIDE)
     *
     * THIS METHOD WILL GET THE REQUEST STRING IN BYTE ARRAY AND CONVERT IT INTO
     * A REGULAR STRING WHICH CONTAINS THE REQUESTING ORDER AND THEN RETURN THIS
     * REQUESTING ORDER STRING.
     *
     * @return REQUESTING ORDER STRING
     */
    @Override
    public String call(){
      try {
        InputStream input  = this._client_socket.getInputStream();
        System.out.println("LocaleHost OK.");

        this._result = new Scanner(input, "UTF-8").useDelimiter("\\A").next();
        System.out.println("REQUEST : " + this._result);
        input.close();

        return this._result;
      }
      catch (IOException e) {
        e.printStackTrace();
      } // END TRY-CATCH
      return null;
    } // END run METHOD

    public final String get_result(){
      return this._result;
    }
  } // END Worker CLASS
  //////////////////////// RUN SERVER //////////////////////////////////////////
  public static void main(String[] args) {
    RequestServer s = new RequestServer("localhost", 8080);
    //s.display_requests();
  }
} // END RequestServer CLASS