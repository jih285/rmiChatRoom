package rmiserver;


import rmiclient.ChatClientIF;

import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class room{
    private String room_name;
    private List<ChatClientIF> members;
    public String chatlog;
    private Date lastUpdate;

    public room(String name){
        this.room_name=name;
        this.members= new ArrayList<ChatClientIF>();
        this.chatlog="";
        this.lastUpdate  = new Date();
    }
    public int size(){
        return this.members.size();
    }
    public void add_member(ChatClientIF member)
    {
        this.members.add(member);
    }

    public void remove_member(ChatClientIF member)
    {
        members.remove(member);
    }

    public String getRoom_name() {
        return room_name;
    }
    public ChatClientIF getMember(int index){
        return this.members.get(index);

    }
    public void updateDate()
    {
        this.lastUpdate=new Date();
    }
    public Date getLastUpdate()
    {
        return this.lastUpdate;
    }
    public boolean ifMemberExisit(Socket member)
    {
        return this.members.contains(member);
    }

}
