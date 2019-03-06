

import java.util.*;

public class Malloc {
    
    
      class Node { 
        int zeroOrOne;
        String data;
        int start;
        int end;
        int size;
        Node next;
        Node head=null;
        
        Node(int ZeroOrOne,String Data,int Start,int End){
            zeroOrOne=ZeroOrOne;
            data = Data;  
            start=Start;
            end=End;
            size=End-Start+1;
            next=null;
        }
        
        void addNode(Node newNode,String frontdata){
            if(head==null){
                head=newNode;
            }else{
                Node temp=head;
                while(temp!=null && !temp.data.equals(frontdata)){
                    temp=temp.next;
                }
                if(temp!=null & temp.data.equals(frontdata)){
                    if(temp.next!=null){
                        Node prevNext=temp.next;
                        temp.next=newNode;
                        newNode.next=prevNext;
                    }else{
                        temp.next=newNode;
                    }
                }
            }
        }
        
        void deleteNode(Node prevToDelete){
            if(prevToDelete.next.next!=null){
                Node nextNode=prevToDelete.next.next;
                prevToDelete.next=nextNode;
            }else{
                prevToDelete.next=null;
            }
            
        }
        
        void printNodes(){
            Node temp=head;
            System.out.println("===========================================================================");
            while(temp!=null){
                System.out.println("===========================================================================");
                System.out.println("#####                                                                ");
                System.out.println("#####  Block ID :    "+temp.data);
                System.out.println("#####  Starting At : "+temp.start );
                System.out.println("#####  End At :      "+temp.end);
                System.out.println("#####  Size :        "+ (temp.end-temp.start+1));
                System.out.println("#####                                                                ");
             
                System.out.println("===========================================================================");
                temp=temp.next;
            }
        }
        
// give the smallest hole larger than the given size or return null
        
        Node GetSmallestHole(int size){
            Node temp=head;
            Node smallestHole = null;
            int smallestSize=25001;
            while(temp!=null){
                if(temp.size<smallestSize && temp.zeroOrOne==0 && temp.size>=size){
                    smallestHole=temp;
                    smallestSize=temp.size;
         
                }
                temp=temp.next;
                
            }
            
            return smallestHole; 
        }
// give previous block to free if it's in end
        Node CheckEndToFree(int pointer){
            Node temp=head;
            while(temp.next.next!=null ){
                temp=temp.next;
            }
            if(temp.next.start==pointer){
                return temp;
            }else{
                return null;
            }
        }
        
// give previous block to free if its in middle
        Node CheckMiddleToFree(int pointer){
            Node temp=head;
            while(temp.next!=null & temp.next.start!=pointer){
                temp=temp.next;
            }
            if(temp.next!=null && temp.next.start==pointer){
                return temp;
            }else{
                return null;
            }
        }
        
        
        int GetZeroOrOne(){
            return zeroOrOne;
        }
        
        String GetData(){
            return data;
        }
        
        int GetStart(){
            return start;
        }
        
        int GetEnd(){
            return end;
        }
        
        Node GetNext(){
            return next;
        }
        
        Node GetHead(){
            return head;
        }
        
        int GetSize(){
            return (end-start+1);
        }
        
         void SetZeroOrOne(int ZeroOrOne){
            zeroOrOne=ZeroOrOne;
        }
        
        void SetData(String Data){
            data=Data;
        }
        
        void SetStart(int Start){
            start=Start;
        }
        
        void SetEnd(int End){
            end=End;
        }
        
        void SetNext(Node n){
            next=n;
        } 
        void SetSize(int Size){
            size=Size;
        } 
       
    }
      
      
    
  int []Memory=new int[25000];
  Node n;
  int i=0;
  int j=0;
  
  
  Malloc(){
     n=new Node(0,"H"+Integer.toString(j++),1,25000);
     n.addNode(n,"H");
  }
  
  void MallocPrintNodes(){
      n.printNodes();
  }
  
 int MyMalloc(int size){
    Node Block=n.GetSmallestHole(size);
    if(Block!=null){
        if(Block.GetSize()==size){
            Block.SetData("P"+Integer.toString(i));
            Block.SetZeroOrOne(1);
        }
        else{
            Node newNode=new Node(0,Block.GetData(),Block.GetStart()+ size,Block.GetEnd());
            Block.SetData("P"+Integer.toString(i));
            Block.SetEnd(Block.GetStart()+size-1);
            Block.SetZeroOrOne(1);
            Block.SetSize(Block.GetEnd()-Block.GetStart()+1);
            n.addNode(newNode,"P"+Integer.toString(i));
            i++;
            if(Block.GetEnd()==25000){
                Block.SetNext(null);
            }
        }
        return Block.GetStart();
        
     }else{
        System.out.println("No Enough Memory!!!!");
        return -1;
    }
 }
  
 void MyFree(int pointer){
     Node Head=n.GetHead();
     int End=Head.GetNext().GetEnd();
     if(Head.GetStart()==pointer && Head.GetNext()!=null){  
         if(Head.GetNext().GetZeroOrOne()==0){
             n.deleteNode(Head);
             Head.SetEnd(End);
             Head.SetZeroOrOne(0);
             Head.SetSize(25000);
         }else{
             Head.SetZeroOrOne(0);
         }
         Head.SetData("H"+Integer.toString(j++));
     }else{
         Node m=n.CheckEndToFree(pointer);
         if(m!=null){
             if(m.GetZeroOrOne()==1){
                 m.GetNext().SetZeroOrOne(0);
                 m.GetNext().SetData("H"+Integer.toString(j++));
             }else{
                 int TotalSize=m.GetSize()+m.GetNext().GetSize();
                 m.SetEnd(m.GetStart()+TotalSize-1);
                 m.SetNext(null);
             }
         }else{
             Node prevToMiddle=n.CheckMiddleToFree(pointer);
             if(prevToMiddle.GetZeroOrOne()==1 && prevToMiddle.GetNext().GetNext().GetZeroOrOne()==1){
                 prevToMiddle.GetNext().SetZeroOrOne(0);
                 prevToMiddle.GetNext().SetData("H"+Integer.toString(j++));
                 
             }else if(prevToMiddle.GetZeroOrOne()==0 && prevToMiddle.GetNext().GetNext().GetZeroOrOne()==0){
                 if(prevToMiddle.GetNext().GetNext().GetNext()==null){
                     prevToMiddle.SetEnd(prevToMiddle.GetNext().GetNext().GetEnd());
                     prevToMiddle.SetNext(null);
                 }else{
                     prevToMiddle.SetEnd(prevToMiddle.GetNext().GetNext().GetEnd());
                     prevToMiddle.SetNext(prevToMiddle.GetNext().GetNext().GetNext());
                 }
             }else if(prevToMiddle.GetZeroOrOne()==1 && prevToMiddle.GetNext().GetNext().GetZeroOrOne()==0){
                 if(prevToMiddle.GetNext().GetNext().GetNext()==null){
                     prevToMiddle.GetNext().SetEnd(prevToMiddle.GetNext().GetNext().GetEnd());
                     prevToMiddle.GetNext().SetNext(null);
                     prevToMiddle.GetNext().SetZeroOrOne(0);
                     prevToMiddle.GetNext().SetData("H"+Integer.toString(j++));
                 }else{
                      prevToMiddle.GetNext().SetEnd(prevToMiddle.GetNext().GetNext().GetEnd());
                      prevToMiddle.GetNext().SetNext(prevToMiddle.GetNext().GetNext());
                      prevToMiddle.GetNext().SetZeroOrOne(0);
                      prevToMiddle.GetNext().SetData("H"+Integer.toString(j++));
                       
                 }
             }else if(prevToMiddle.GetZeroOrOne()==0 && prevToMiddle.GetNext().GetNext().GetZeroOrOne()==1){
                 prevToMiddle.SetEnd(prevToMiddle.GetNext().GetEnd());
                 prevToMiddle.SetNext(prevToMiddle.GetNext().GetNext());
             }  
             
         }
     }
 }
  
    public static void main(String[] args) {
       
        Malloc m=new Malloc();
       String input;
       int size;
        while(true){
            Scanner sc=new Scanner(System.in);
            System.out.println("Enter P to Add Process \nEnter F to Free a Block  :- ");
            input=sc.nextLine();
            if(null != input)switch (input) {
                case "P":
                    System.out.print("Enter Size Of Block  :-  ");
                    Scanner sc1=new Scanner(System.in);
                    size=sc1.nextInt();
                    m.MyMalloc(size);
                    m.MallocPrintNodes();
                    System.out.println("\n\n\n");
                    break;
                case "F":
                    System.out.print("Enter the Starting Byte of the Block to Free  :-  ");
                    Scanner sc2=new Scanner(System.in);
                    size=sc2.nextInt();
                    m.MyFree(size);
                    m.MallocPrintNodes();
                    System.out.println("\n\n\n");
                    break;
                default:
                    System.out.println("Retry Please  !!!");
                    break;
            }
            
        }
        
    }   
}
