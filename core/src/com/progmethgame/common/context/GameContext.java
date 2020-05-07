package com.progmethgame.common.context;

/**
 * Store global game context ie. ServerContext and ClientContext
 */
public final class GameContext {
	
	/** Server context */
	private static ServerContext serverCtx;
	
	/** Client context */
	private static ClientContext clientCtx;
	
	private GameContext() {} //Singleton
	
	/**
	 * Set the global server context.
	 * @param ctx
	 */
	public static void setServerContext(ServerContext ctx) {
		GameContext.serverCtx = ctx;
	}
	
	/**
	 * Set the global client context.
	 * @param ctx
	 */
	public static void setClientContext(ClientContext ctx) {
		GameContext.clientCtx = ctx;
	}
	
	/**
	 * Get current server context
	 * @return
	 */
	public static ServerContext getServerContext() {
		return serverCtx;
	}
	
	/**
	 * Get current client context
	 * @return
	 */
	public static ClientContext getClientContext() {
		return clientCtx;
	}
}
