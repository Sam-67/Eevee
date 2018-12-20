package Domain;

public enum EmployeRole {
	CEO {
		public String toString() {
			return "CEO";
		}
	},

	PROJECT_MANAGER {
		public String toString() {
			return "chef de projet";
		}
	},

	DEVELLOPER {
		public String toString() {
			return "développeur";
		}
	},

	TECHNICAL_MANAGER {
		public String toString() {
			return "manager technique";
		}
	},
}
