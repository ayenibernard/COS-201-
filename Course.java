class Course {
    private String courseCode;
    private String courseTitle;
    private int unit;

    public Course(String courseCode, String courseTitle, int unit) {
        // Simple string processing: trimming whitespace and standardizing casing
        this.courseCode = courseCode.trim().toUpperCase();
        this.courseTitle = courseTitle.trim();
        this.unit = unit;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public int getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return String.format("%-12s | %-30s | %-5d", courseCode, courseTitle, unit);
    }
}