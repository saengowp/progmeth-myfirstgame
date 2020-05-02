package com.progmethgame.common.context;

/**
 * Store global game context ie. ServerContext and ClientContext
 *
 */
public final class GameContext {
	
	private static ServerContext serverCtx;
	private static ClientContext clientCtx;
	
	private GameContext() {}
	
	public static void setServerContext(ServerContext ctx) {
		GameContext.serverCtx = ctx;
	}
	
	public static void setClientContext(ClientContext ctx) {
		GameContext.clientCtx = ctx;
	}
	
	public static ServerContext getServerContext() {
		return serverCtx;
	}
	
	public static ClientContext getClientContext() {
		return clientCtx;
	}
}
