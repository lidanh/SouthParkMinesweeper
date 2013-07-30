package minesweeper.model;

/**
 *  Field Value- zero, mine or number
 *  @author     Lidan Hifi
 *  @version    1.0
 */
public enum FieldValue {
	ZERO {
		/*
		 * (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return "";
		}

		/*
		 * (non-Javadoc)
		 * @see minesweeper.model.FieldValue#nextValue()
		 */
		@Override
		public FieldValue nextValue() {
			return ONE;
		}
	},
	ONE {
		/*
		 * (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return "1";
		}
		
		/*
		 * (non-Javadoc)
		 * @see minesweeper.model.FieldValue#nextValue()
		 */
		@Override
		public FieldValue nextValue() {
			return TWO;
		}
	},
	TWO {
		/*
		 * (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return "2";
		}
		
		/*
		 * (non-Javadoc)
		 * @see minesweeper.model.FieldValue#nextValue()
		 */
		@Override
		public FieldValue nextValue() {
			return THREE;
		}
	},
	THREE {
		/*
		 * (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return "3";
		}
		
		/*
		 * (non-Javadoc)
		 * @see minesweeper.model.FieldValue#nextValue()
		 */
		@Override
		public FieldValue nextValue() {
			return FOUR;
		}
	},
	FOUR {
		/*
		 * (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return "4";
		}
		
		/*
		 * (non-Javadoc)
		 * @see minesweeper.model.FieldValue#nextValue()
		 */
		@Override
		public FieldValue nextValue() {
			return FIVE;
		}
	},
	FIVE {
		/*
		 * (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return "5";
		}
		
		/*
		 * (non-Javadoc)
		 * @see minesweeper.model.FieldValue#nextValue()
		 */
		@Override
		public FieldValue nextValue() {
			return SIX;
		}
	},
	SIX {
		/*
		 * (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return "6";
		}
		
		/*
		 * (non-Javadoc)
		 * @see minesweeper.model.FieldValue#nextValue()
		 */
		@Override
		public FieldValue nextValue() {
			return SEVEN;
		}
	},
	SEVEN {
		/*
		 * (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return "7";
		}
		
		/*
		 * (non-Javadoc)
		 * @see minesweeper.model.FieldValue#nextValue()
		 */
		@Override
		public FieldValue nextValue() {
			return EIGHT;
		}
	},
	EIGHT {
		/*
		 * (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return "8";
		}
		
		/*
		 * (non-Javadoc)
		 * @see minesweeper.model.FieldValue#nextValue()
		 */
		@Override
		public FieldValue nextValue() {
			return EIGHT;
		}
	},
	MINE {
		/*
		 * (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return "M";
		}
		
		/*
		 * (non-Javadoc)
		 * @see minesweeper.model.FieldValue#nextValue()
		 */
		@Override
		public FieldValue nextValue() {
			return MINE;
		}
	};
	
	/**
	 * next value of the field (when we want to increment the field by 1)
	 * @return the next value
	 */
	public abstract FieldValue nextValue();
}