import type { Metadata } from "next";
import "./globals.css";
import Navbar from "@/components/Navbar";
import Footer from "@/components/Footer";

export const metadata: Metadata = {
  title: "Java Hub — The Ultimate Java Learning & Interview Preparation Platform",
  description:
    "Master every Java concept from JDK 1.0 to Java 26. Explore versions, core topics, frameworks, and code examples. Your one-stop revision companion for Java developer interviews.",
  keywords: [
    "Java", "Java Hub", "Java Interview", "Java Learning", "Spring Boot",
    "Java Versions", "Java 21", "Java 26", "OOP", "Collections",
    "Streams", "Lambda", "Design Patterns", "JVM"
  ],
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body>
        <Navbar />
        <div className="page-wrapper">
          {children}
        </div>
        <Footer />
      </body>
    </html>
  );
}
