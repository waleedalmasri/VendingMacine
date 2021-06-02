package MoneySlot;

import Money.Coin;
import Money.Note;

public class NoteSlot {
    private NoteBalance noteBalance;

    public NoteSlot() {
        this.noteBalance = new NoteBalance();
    }

    public NoteBalance getNoteBalance() {
        return noteBalance;
    }

    public void setNoteBalance(NoteBalance noteBalance) {
        this.noteBalance = noteBalance;
    }

    public void updateNoteBalance(Note note) {

        switch (note.getCategory()) {
            case '$':
                switch (note.getNoteValue()) {
                    case 20:
                        this.noteBalance.setNumberOf20Dollars(this.getNoteBalance().getNumberOf20Dollars() + 1);
                        break;
                    case 50:
                        this.noteBalance.setNumberOf50Dollars(this.getNoteBalance().getNumberOf50Dollars() + 1);
                        break;
                }
        }

    }
}
