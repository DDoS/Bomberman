package ecse321.fall2014.group3.bomberman.input;

//import com.flowpowered.commons.ticking.TickingElement;

import ecse321.fall2014.group3.bomberman.Game;

import java.io.IOException;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.flowpowered.commons.queue.SubscribableQueue;
import com.flowpowered.commons.ticking.TickingElement;

import jline.console.ConsoleReader;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

/**
 *
 */
public class Input extends TickingElement {
   private static final ConsoleReaderThread readerThread = new ConsoleReaderThread();
   private static final int TPS = 60;
   private final Game game;
   private boolean mouseCreated = false, keyboardCreated = false;
   private final SubscribableQueue<KeyboardState> keyboardQueue = new SubscribableQueue<>(false);
   private final ConsoleCommandSender sender;
   private Keyboard keyboard = new Keyboard();

   public Input(Game game) {
      super("input", TPS);
      this.game = game;
   }

   @Override
   public void onStart() {  
      keyboardQueue.becomePublisher();
   
      if (!readerThread.isAlive()) {
         if (!readerThread.ranBefore) {
         }
      } 
      else {
         readerThread.getRawCommandQueue().clear();
      }
   }

   @Override
   public void onTick(long dt) {
      if(isCloseRequested()){
          game.close();
      }
          
      createInputIfNecessary();
      if (keyboardCreated) {
         // For every keyboard event
         while (Keyboard.next()) {
            // Create a new event
             switch(Keyboard.getEventKey()){
                case Keyboard.KEY_LEFT:
                    keyboardQueue.add(new KeyboardState(Key.LEFT, Keyboard.getEventKeyState(), Keyboard.getEventNanoseconds()));
                    break;
                case Keyboard.KEY_RIGHT:
                    keyboardQueue.add(new KeyboardState(Key.RIGHT, Keyboard.getEventKeyState(), Keyboard.getEventNanoseconds()));
                    break;
                case Keyboard.KEY_UP:
                    keyboardQueue.add(new KeyboardState(Key.UP, Keyboard.getEventKeyState(), Keyboard.getEventNanoseconds()));
                    break;
                case Keyboard.KEY_DOWN:
                    keyboardQueue.add(new KeyboardState(Key.DOWN, Keyboard.getEventKeyState(), Keyboard.getEventNanoseconds()));
                    break;
                case Keyboard.KEY_SPACE: //lays bombs
                    keyboardQueue.add(new KeyboardState(Key.SPACE, Keyboard.getEventKeyState(), Keyboard.getEventNanoseconds()));
                    break;
                case Keyboard.KEY_P:   //pauses game
                    break;
                case Keyboard.KEY_ESCAPE:   //exit game
                    game.close();
                    break;
            }
         }
      }
   
      final Iterator<String> iterator = readerThread.getRawCommandQueue().iterator();
      while (iterator.hasNext()) {
         final String command = iterator.next();
            //FILL IN COMMAND FLOW
         iterator.remove();
      }
   }

   private void createInputIfNecessary() {
      if (!keyboardCreated) {
         if (Display.isCreated()) {
            if (!Keyboard.isCreated()) {
               try {
                  Keyboard.create();
                  keyboardCreated = true;
               } 
               catch (LWJGLException ex) {
                  throw new RuntimeException("Could not create keyboard", ex);
               }
            } 
            else {
               keyboardCreated = true;
            }
         }
      }
   }

   @Override
   public void onStop() {
      game.close();
      if (Keyboard.isCreated()) {
         Keyboard.destroy();
      }
      keyboardCreated = false;
      keyboardQueue.unsubscribeAll();
   }

   public Queue<KeyboardState> getKeyboardQueue() {
      return keyboardQueue;
   }

   public boolean isCloseRequested() {
      return Display.isCreated() && Display.isCloseRequested();
   }
   
   public boolean isKeyDown(int key) {
      return Keyboard.isCreated() && Keyboard.isKeyDown(key);
   }

   public Game getGame() {
      return game;
   }

   // TODO: this shouldn't be exposed, not thread safe, doesn't fit into the threading model either
   public void clear() throws IOException {
      readerThread.getConsole().clearScreen();
   }

   private static class ConsoleReaderThread extends Thread {
      private volatile boolean running = false;
      private volatile boolean ranBefore = false;
      private final ConsoleReader reader;
      private final ConcurrentLinkedQueue<String> rawCommandQueue = new ConcurrentLinkedQueue<>();
   
      public ConsoleReaderThread() {
         super("command");
         setDaemon(true);
      
         try {
            reader = new ConsoleReader(System.in, System.out);
            reader.setBellEnabled(false);
            reader.setExpandEvents(false);
         } 
         catch (Exception e) {
            throw new RuntimeException("Exception caught creating the console reader!", e);
         }
      }
   
      @Override
      public void run() {
         ranBefore = true;
         running = true;
         try {
            while (running) {
               // TODO: this is broken in when using "gradle run", gets spammed to hell
               //String command = reader.readLine(">");
               String command = reader.readLine();
            
               if (command == null || command.trim().length() == 0) {
                  continue;
               }
            
               rawCommandQueue.offer(command);
            }
         } 
         catch (IOException e) {
            reader.shutdown();
         }
      }
   
      public ConsoleReader getConsole() {
         return reader;
      }
   
      public ConcurrentLinkedQueue<String> getRawCommandQueue() {
         return rawCommandQueue;
      }
   }
}