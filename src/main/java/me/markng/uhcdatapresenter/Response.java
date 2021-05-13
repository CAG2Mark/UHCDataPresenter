package me.markng.uhcdatapresenter;

import java.util.ArrayList;
import java.util.List;

public class Response {
	public List<PlayerInfo> players=new ArrayList<>();
	public PlayerInfo curPlayer;
	public List<Death> deaths=new ArrayList<>();
}