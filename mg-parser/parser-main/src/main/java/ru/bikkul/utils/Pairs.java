package ru.bikkul.utils;

public enum Pairs {
    ETH_BTC("ETH-BTC"),
    ETH_USDT("ETH-USDT"),
    BTC_USDT("BTC-USDT"),
    IMX_USDT("IMX-USDT"),
    XDC_USDT("XDC-USDT"),
    STX_USDT("STX-USDT"),
    USDD_USDT("USDD-USDT"),
    BSV_USDT("BSV-USDT"),
    ALGO_USDT("ALGO-USDT"),
    GRT_USDT("GRT-USDT"),
    CRV_USDT("CRV-USDT"),
    AAVE_USDT("AAVE-USDT"),
    RNDR_USDT("RNDR-USDT"),
    XTZ_USDT("XTZ-USDT"),
    INJ_USDT("INJ-USDT"),
    AXS_USDT("AXS-USDT"),
    EGLD_USDT("EGLD-USDT"),
    EOS_USDT("EOS-USDT"),
    THETA_USDT("THETA-USDT"),
    SAND_USDT("SAND-USDT"),
    MANA_USDT("MANA-USDT"),
    FTM_USDT("FTM-USDT"),
    SNX_USDT("SNX-USDT"),
    OP_USDT("OP-USDT"),
    KAVA_USDT("KAVA-USDT"),
    NEO_USDT("NEO-USDT"),
    PAXG_USDT("PAXG-USDT"),
    XAUT_USDT("XAUT-USDT"),
    FLOW_USDT("FLOW-USDT"),
    CHZ_USDT("CHZ-USDT"),
    ZEC_USDT("ZEC-USDT"),
    OCEAN_USDT("OCEAN-USDT"),
    CFX_USDT("CFX-USDT"),
    APE_USDT("APE-USDT"),
    RPL_USDT("RPL-USDT"),
    SOL_USDT("SOL-USDT"),
    ADA_USDT("ADA-USDT"),
    DOGE_USDT("DOGE-USDT"),
    TRX_USDT("TRX-USDT"),
    MATIC_USDT("MATIC-USDT"),
    ASTR_USDT("ASTR-USDT"),
    MX_USDT("MX-USDT");

    private final String pair;

    Pairs(String pair) {
        this.pair = pair;
    }

    public String getPair() {
        return pair;
    }
}


