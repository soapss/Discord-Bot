package Main.Misc;

//set the emoji with java codepoint values and emoji category type
public enum Emoji {

    HASH("\u0023", EmojiCategory.CHARACTER),
    ZERO("\u0030", EmojiCategory.CHARACTER),
    ONE("\u0031\uFE0F\u20E3", EmojiCategory.SELECTION),
    TWO("\u0032\uFE0F\u20E3", EmojiCategory.SELECTION),
    THREE("\u0033\uFE0F\u20E3", EmojiCategory.SELECTION),
    FOUR("\u0034\uFE0F\u20E3", EmojiCategory.SELECTION),
    FIVE("\u0035\uFE0F\u20E3", EmojiCategory.SELECTION),
    SIX("\u0036\uFE0F\u20E3", EmojiCategory.SELECTION),
    SEVEN("\u0037\uFE0F\u20E3", EmojiCategory.SELECTION),
    EIGHT("\u0038\uFE0F\u20E3", EmojiCategory.SELECTION),
    NINE("\u0039\uFE0F\u20E3", EmojiCategory.SELECTION),

    GRINNING("\uD83D\uDE00", EmojiCategory.EMOTION),
    GRIN("\uD83D\uDE01", EmojiCategory.EMOTION),
    JOY("\uD83D\uDE02", EmojiCategory.EMOTION),
    SMILEY("\uD83D\uDE03", EmojiCategory.EMOTION),
    SMILE("\uD83D\uDE04", EmojiCategory.EMOTION),
    SWEAT_SMILE("\uD83D\uDE05", EmojiCategory.EMOTION),
    LAUGHING("\uD83D\uDE06", EmojiCategory.EMOTION),
    INNOCENT("\uD83D\uDE07", EmojiCategory.EMOTION),
    SMILING_IMP("\uD83D\uDE08", EmojiCategory.EMOTION),
    WINK("\uD83D\uDE09", EmojiCategory.EMOTION),
    BLUSH("\uD83D\uDE0A", EmojiCategory.EMOTION),
    YUM("\uD83D\uDE0B", EmojiCategory.EMOTION),
    RELIEVED("\uD83D\uDE0C", EmojiCategory.EMOTION),
    HEART_EYES("\uD83D\uDE0D", EmojiCategory.EMOTION),
    SUNGLASSES("\uD83D\uDE0E", EmojiCategory.EMOTION),
    SMIRK("\uD83D\uDE0F", EmojiCategory.EMOTION),
    NEUTRAL("\uD83D\uDE10", EmojiCategory.EMOTION),
    EXPRESSIONLESS("\uD83D\uDE11", EmojiCategory.EMOTION),
    UNAMUSED("\uD83D\uDE12", EmojiCategory.EMOTION),
    SWEAT("\uD83D\uDE13", EmojiCategory.EMOTION),
    PENSIVE("\uD83D\uDE14", EmojiCategory.EMOTION),
    CONFUSED("\uD83D\uDE15", EmojiCategory.EMOTION),
    CONFOUNDED("\uD83D\uDE16", EmojiCategory.EMOTION),
    KISSING("\uD83D\uDE17", EmojiCategory.EMOTION),
    KISSING_HEART("\uD83D\uDE18", EmojiCategory.EMOTION),
    KISSING_SMILE_EYES("\uD83D\uDE19", EmojiCategory.EMOTION),
    KISSING_CLOSED_EYES("\uD83D\uDE1A", EmojiCategory.EMOTION),
    TONGUE("\uD83D\uDE1B", EmojiCategory.EMOTION),
    TONGUE_WINK_EYE("\uD83D\uDE1C", EmojiCategory.EMOTION),
    TONGUE_CLOSED_EYES("\uD83D\uDE1D", EmojiCategory.EMOTION),
    DISAPPOINTED("\uD83D\uDE1E", EmojiCategory.EMOTION),
    WORRIED("\uD83D\uDE1F", EmojiCategory.EMOTION),
    ANGRY("\uD83D\uDE20", EmojiCategory.EMOTION),
    RAGE("\uD83D\uDE21", EmojiCategory.EMOTION),
    CRY("\uD83D\uDE22", EmojiCategory.EMOTION),
    PERSEVERE("\uD83D\uDE23", EmojiCategory.EMOTION),
    TRIUMPH("\uD83D\uDE24", EmojiCategory.EMOTION),
    DISAPPOINTED_RELIEVED("\uD83D\uDE25", EmojiCategory.EMOTION),
    FROWNING("\uD83D\uDE26", EmojiCategory.EMOTION),
    ANGUISHED("\uD83D\uDE27", EmojiCategory.EMOTION),
    FEARFUL("\uD83D\uDE28", EmojiCategory.EMOTION),
    WEARY("\uD83D\uDE29", EmojiCategory.EMOTION),
    SLEEPY("\uD83D\uDE2A", EmojiCategory.EMOTION),
    TIRED("\uD83D\uDE2B", EmojiCategory.EMOTION),
    GRIMACING("\uD83D\uDE2C", EmojiCategory.EMOTION),
    SOB("\uD83D\uDE2D", EmojiCategory.EMOTION),
    OPEN_MOUTH("\uD83D\uDE2E", EmojiCategory.EMOTION),
    HUSHED("\uD83D\uDE2F", EmojiCategory.EMOTION),
    COLD_SWEAT("\uD83D\uDE30", EmojiCategory.EMOTION),
    SCREAM("\uD83D\uDE31", EmojiCategory.EMOTION),
    ASTONISHED("\uD83D\uDE32", EmojiCategory.EMOTION),
    FLUSHED("\uD83D\uDE33", EmojiCategory.EMOTION),
    SLEEPING("\uD83D\uDE34", EmojiCategory.EMOTION),
    DIZZY_FACE("\uD83D\uDE35", EmojiCategory.EMOTION),
    NO_MOUTH("\uD83D\uDE36", EmojiCategory.EMOTION),
    MASK("\uD83D\uDE37", EmojiCategory.EMOTION),

    FAST_FORWARD("\u23E9", EmojiCategory.MUSIC),
    REWIND("\u23EA", EmojiCategory.MUSIC),
    NEXT("\u23ED", EmojiCategory.MUSIC),
    ARROW_FORWARD("\u25B6", EmojiCategory.MUSIC),
    ARROW_BACKWARD("\u25C0", EmojiCategory.MUSIC),
    ARROW_UP("\u2B06", EmojiCategory.PAGE),
    ARROW_DOWN("\u2B07", EmojiCategory.PAGE),
    ARROW_LEFT("\u2B05", EmojiCategory.PRIVATE),
    ARROW_RIGHT("\u27A1", EmojiCategory.PRIVATE),
    DISABLE("\u26D4\uFE0F", EmojiCategory.MUSIC),
    MARKER("\uD83D\uDD38", EmojiCategory.MUSIC),
    PAUSE("\u23F8", EmojiCategory.MUSIC),
    STOP("\u23F9", EmojiCategory.MUSIC),
    SHUFFLE("\uD83D\uDD00", EmojiCategory.MUSIC),
    REPEAT("\uD83D\uDD01", EmojiCategory.MUSIC),
    LINE("\u2796",EmojiCategory.MUSIC),

    OK("\uD83D\uDC4C", EmojiCategory.REACTION),
    CHECK("\u2705", EmojiCategory.REACTION),
    CROSS("\u274E", EmojiCategory.REACTION),
    BAN("\uD83D\uDEAB", EmojiCategory.REACTION),
    CIRCLE("<:circle:733231942325108816>",EmojiCategory.REACTION),

    WIN("\u2B55",EmojiCategory.MISC),
    LOSS("\u274C",EmojiCategory.MISC),
    ;

    private final String code;
    private final EmojiCategory cat;

//format for the stuff above
    Emoji(String code, EmojiCategory cat) {
        //setting the values
        this.code = code;
        this.cat = cat;
    }

    //getting emoji code
    public String getEmoji(){
        return code;
    }

    //getting emoji category
    public EmojiCategory getCategory(){
        return cat;
    }
}
