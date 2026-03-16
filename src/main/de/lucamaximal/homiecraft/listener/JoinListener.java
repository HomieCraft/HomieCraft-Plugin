@EventHandler
public void onJoin(PlayerJoinEvent event) {

    if(!event.getPlayer().hasPlayedBefore()) {

        MessageUtils.send(event.getPlayer(), Messages.FIRST_JOIN);
        return;

    }

    MessageUtils.send(event.getPlayer(), Messages.JOIN);

}
