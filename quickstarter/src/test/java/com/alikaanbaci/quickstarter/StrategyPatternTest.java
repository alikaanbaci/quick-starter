package com.alikaanbaci.quickstarter;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StrategyPatternTest {

    interface TradingStrategy {

        String execute();
    }

    class BullishStrategy implements TradingStrategy {

        @Override
        public String execute() {
            return "Bullish strategy executing";
        }
    }

    class BearishStrategy implements TradingStrategy {

        @Override
        public String execute() {
            return "Bearish strategy executing";
        }
    }

    @AllArgsConstructor
    class TradeExecutor {

        private TradingStrategy tradingStrategy;

        void changeStrategy(TradingStrategy tradingStrategy) {
            this.tradingStrategy = tradingStrategy;
        }

        public String trade() {
            return tradingStrategy.execute();
        }
    }

    @Test
    void runTest() {
        // Prepare
        final TradeExecutor tradeExecutor = new TradeExecutor(new BullishStrategy());
        var bullishTrading = tradeExecutor.trade();
        tradeExecutor.changeStrategy(new BearishStrategy());
        var bearishTrading = tradeExecutor.trade();
        // Verify
        assertThat(bullishTrading).isEqualTo("Bullish strategy executing");
        assertThat(bearishTrading).isEqualTo("Bearish strategy executing");
    }
}
