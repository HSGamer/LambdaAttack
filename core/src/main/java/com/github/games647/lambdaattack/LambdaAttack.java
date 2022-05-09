package com.github.games647.lambdaattack;

import com.github.games647.lambdaattack.bot.AbstractBot;
import com.github.games647.lambdaattack.bot.BotCreator;
import com.github.games647.lambdaattack.profile.Profile;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LambdaAttack {

    public static final String PROJECT_NAME = "LambdaAttack";
    public static final String VERSION = LambdaAttack.class.getPackage().getImplementationVersion();

    private static final Logger LOGGER = Logger.getLogger(PROJECT_NAME);
    private static final LambdaAttack instance = new LambdaAttack();
    private final List<AbstractBot> clients = new ArrayList<>();
    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    private boolean running = false;

    private List<Proxy> proxies;
    private List<String> names;

    public static Logger getLogger() {
        return LOGGER;
    }

    public static LambdaAttack getInstance() {
        return instance;
    }

    public void start(Options options) {
        running = true;

        BotCreator creator = options.gameVersion.getLoader().load().join();
        if (creator == null) {
            LOGGER.log(Level.SEVERE, "Failed to load the bot creator for {0}", options.gameVersion.getVersion());
            running = false;
            return;
        }

        for (int i = 0; i < options.amount; i++) {
            String username = String.format(options.botNameFormat, i);
            if (names != null) {
                if (names.size() <= i) {
                    LOGGER.warning("Amount is higher than the name list size. Limitting amount size now...");
                    break;
                }

                username = names.get(i);
            }

            Profile profile = new Profile(username, "");

            AbstractBot bot;
            if (proxies != null) {
                Proxy proxy = proxies.get(i % proxies.size());
                bot = creator.createBot(options.botOptions, profile, proxy);
            } else {
                bot = creator.createBot(options.botOptions, profile);
            }
            bot.getLogger().setParent(LOGGER);

            this.clients.add(bot);
        }

        for (AbstractBot client : clients) {
            try {
                TimeUnit.MILLISECONDS.sleep(options.joinDelayMs);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            if (!running) {
                break;
            }

            client.connect(options.hostname, options.port);
        }
    }

    public void setProxies(List<Proxy> proxies) {
        this.proxies = proxies;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public void stop() {
        this.running = false;
        clients.forEach(AbstractBot::disconnect);
        clients.clear();
    }

    public ExecutorService getThreadPool() {
        return threadPool;
    }
}
