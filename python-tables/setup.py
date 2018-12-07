if __name__ == "__main__":
    import setuptools

    with open("README.md", "r") as fh:
        long_description = fh.read()

    setuptools.setup(
        name="w7py",
        version="0.0.1",
        description="w7py",
        author="author",
        author_email="author@example.com",
        long_description=long_description,
        url="https://github.com/yuliu2016/w7py",
        packages=setuptools.find_packages(),
        classifiers=[
            "Programming Language :: Python :: 3",
            "Operating System :: OS Independent",
        ],
        install_requires=[
            "numpy",
            "pandas",
            "requests"
        ],
        python_requires=">=3.5",
        entry_points={
            'console_scripts': ['w7=w7py.cli:cli_main']
        }
    )
